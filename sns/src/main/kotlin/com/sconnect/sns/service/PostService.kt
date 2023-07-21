package com.sconnect.sns.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sconnect.sns.client.AuthServiceClient
import com.sconnect.sns.model.dto.RequestTokenDto
import com.sconnect.sns.model.dto.ResponseTokenDto
import com.sconnect.sns.model.entity.Image
import com.sconnect.sns.model.entity.Post
import com.sconnect.sns.repository.ImageRepository
import com.sconnect.sns.repository.PostRepository
import com.sconnect.sns.request.CreatePostRequest
import com.sconnect.sns.response.AccountResponse
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
        private val postRepository: PostRepository,
        private val authServiceClient: AuthServiceClient,
        private val imageRepository: ImageRepository
) {
    fun createPost(authorization: String, createPostRequest: CreatePostRequest): Post {
        // "Authorization" 헤더에서 "Bearer"를 제거하고 토큰만 추출
        val token = authorization.removePrefix("Bearer ").trim()
        //이미지 id 추출
        val image: Image =  imageRepository.findById(createPostRequest.imageId).orElseThrow()
        val accountResponse: AccountResponse = authServiceClient.getAccountInfo(token)

        // 게시글 생성
        val post = Post(
                title = createPostRequest.title,
                content = createPostRequest.content,
                imageUrl = createPostRequest.imageUrl,
                userId = accountResponse.accountId,
                image = image,
                userName = accountResponse.nickname
        )

        //Feign을 이용해서 사용자 인증 서비스에 토큰 검증 요청

        return postRepository.save(post) // 먼저 게시글을 저장하고, 게시물 ID를 가져옵니다.



        /* 토큰 검증 요청을 Kafka로 전송 (비동기)
        val mapper = jacksonObjectMapper()

        val jsonString = mapper.writeValueAsString(RequestTokenDto(token, savedPost.postId))

        kafkaTemplate.send("token-request", jsonString)

        return savedPost

         */
    }

    //전체 검색 + 최신순 보여주기
    fun getPosts(page: Int, size: Int, search: String?, sort: String? = "createdAt"): List<PostResponse> {
        //DB에 저장된 Post 데이터를 최신순으로 가져옴.
        //사용자 인증이 필요없음. 진짜로 최신순 게시글을 불러오는 것이므로
        val pageRequest: Pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, sort)
        val posts: Page<Post> = if (search != null) {
            postRepository.findAllByContentContaining(pageRequest, search)
        } else {
            postRepository.findAll(pageRequest)
        }
        val postResponses = mutableListOf<PostResponse>()
        posts.forEach { postResponses.add(PostResponse(it)) }
        return postResponses
    }

    fun getPost(postId: Long): PostResponse {
        return PostResponse(postRepository.findById(postId)
                .orElseThrow { throw IllegalArgumentException("Invalid post ID") })
    }
    fun getValidatedPost(postId: Long): Post {
        return postRepository.findById(postId)
                .orElseThrow { throw IllegalArgumentException("Invalid post ID") }
    }

    fun getRecommendedPosts(postId: Long): List<Post>{
        //redis로부터 추천 게시글 id목록을 가져와서 게시글 List를 만들어 반환
        return postRepository.findAllById(listOf(1L, 2L, 3L))
    }


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

    fun savePost(post: Post): Post {
        return postRepository.save(post)
    }

    fun getUserInfoFromToken(authorization: String): AccountResponse {
        //Feign을 이용해 auth서버에 요청
        return authServiceClient.getAccountInfo(authorization)

    }
}

