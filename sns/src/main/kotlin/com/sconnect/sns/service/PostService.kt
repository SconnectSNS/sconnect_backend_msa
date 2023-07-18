package com.sconnect.sns.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sconnect.sns.model.dto.RequestTokenDto
import com.sconnect.sns.model.entity.Post
import com.sconnect.sns.repository.PostRepository
import com.sconnect.sns.request.CreatePostRequest
import com.sconnect.sns.response.PostResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class PostService(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val postRepository: PostRepository
) {
    fun createPost(authorization: String, createPostRequest: CreatePostRequest): Post {
        // "Authorization" 헤더에서 "Bearer"를 제거하고 토큰만 추출
        val token = authorization.removePrefix("Bearer ").trim()

        // 게시글 생성
        val post = Post(
            title = createPostRequest.title,
            content = createPostRequest.content,
            imageUrl = createPostRequest.imageUrl,
            userId = null,
            imageData = "",
            userName = ""
        )

        val savedPost = postRepository.save(post) // 먼저 게시글을 저장하고, 게시물 ID를 가져옵니다.

        // 토큰 검증 요청을 Kafka로 전송 (비동기)
        val mapper = jacksonObjectMapper()

        val jsonString = mapper.writeValueAsString(RequestTokenDto(token, savedPost.id))

        kafkaTemplate.send("token-request", jsonString)

        return savedPost
    }

    fun getPosts(page:Int, size:Int, sort:String?): List<PostResponse>{
        //DB에 저장된 Post 데이터를 최신순으로 가져옴.
        //사용자 인증이 필요없음. 진짜로 최신순 게시글을 불러오는 것이므로
        //TODO: 검색 글을 가져오는 방식
        val pageRequest: Pageable = PageRequest.of(page-1, size, Sort.Direction.DESC, sort)
        val posts: Page<Post> = postRepository.findAll(pageRequest)
        val postResponses= mutableListOf<PostResponse>()
        posts.forEach { postResponses.add(PostResponse(it)) }
        return postResponses
    }

    fun getPost(postId: Long): PostResponse {
        return PostResponse(postRepository.findById(postId)
            .orElseThrow { throw IllegalArgumentException("Invalid post ID") })
    }

    /*
    // TODO: 추천 게시글 저장된거 가져오기
    fun getSuggestedPostsOfPost(postId: Long): List<PostResponse> {
        val post = postRepository.findById(postId)
            .orElseThrow { throw IllegalArgumentException("Invalid post ID") }
        val posts = postRepository.findAllByUserId(post.userId)
        val postResponses = mutableListOf<PostResponse>()
        posts.forEach { postResponses.add(PostResponse(it)) }
        return postResponses
    }
     */


    // 토큰 유효성 검증에 실패하면 호출되는 메소드
    fun rollbackPost(postId: Long) {
        val post = postRepository.findById(postId)
            .orElseThrow { throw IllegalArgumentException("Invalid post ID") }
        postRepository.delete(post)
    }

    fun completePost(postId: Long, userId: String, nickname: String) {
        val post = postRepository.findById(postId)
            .orElseThrow { throw IllegalArgumentException("Invalid post ID") }
        post.userId = userId.toLong()
        post.userName = nickname
        postRepository.save(post)
    }
}

