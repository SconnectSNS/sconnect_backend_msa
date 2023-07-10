package com.sconnect.sns.service

import com.sconnect.sns.model.entity.Post
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class TokenValidationResultListener(
    private val postService: PostService
) {

    @KafkaListener(topics = ["token-responses"], groupId = "group_id")
    fun handleValidationResult(payload: Map<String, Any>) {
        val postId = payload["postId"] as Long
        val isValid = payload["isValid"] as Boolean

        // 검증 결과 처리
        if (!isValid) {
            // 검증 실패, 게시물 롤백
            postService.rollbackPost(postId)
        }
    }
}


