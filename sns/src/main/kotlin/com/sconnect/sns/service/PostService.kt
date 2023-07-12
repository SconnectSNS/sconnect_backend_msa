package com.sconnect.sns.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sconnect.sns.model.dto.RequestTokenDto
import com.sconnect.sns.model.entity.Post
import com.sconnect.sns.repository.PostRepository
import com.sconnect.sns.request.CreatePostRequest
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
            userId = null
        )

        val savedPost = postRepository.save(post) // 먼저 게시글을 저장하고, 게시물 ID를 가져옵니다.

        // 토큰 검증 요청을 Kafka로 전송 (비동기)
        val mapper = jacksonObjectMapper()

        val jsonString = mapper.writeValueAsString(RequestTokenDto(token, savedPost.id))

        kafkaTemplate.send("token-request", jsonString)

        return savedPost
    }

    // 토큰 유효성 검증에 실패하면 호출되는 메소드
    fun rollbackPost(postId: Long) {
        val post = postRepository.findById(postId)
            .orElseThrow { throw IllegalArgumentException("Invalid post ID") }
        postRepository.delete(post)
    }

    fun completePost(postId: Long, userId: String) {
        val post = postRepository.findById(postId)
            .orElseThrow { throw IllegalArgumentException("Invalid post ID") }
        post.userId = userId.toLong()
        postRepository.save(post)
    }
}

