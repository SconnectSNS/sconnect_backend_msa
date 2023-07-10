package com.sconnect.sns.service

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

        // 토큰 검증 요청을 Kafka로 전송
        kafkaTemplate.send("token-requests", token)
        val post = Post(
            title = createPostRequest.title,
            content = createPostRequest.content,
            imageUrl = createPostRequest.imageUrl
        )
        return postRepository.save(post)
    }
}