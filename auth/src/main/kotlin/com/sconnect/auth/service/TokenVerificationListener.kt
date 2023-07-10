package com.sconnect.auth.service

import com.sconnect.auth.security.JwtTokenProvider
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class TokenValidationListener(
    private val jwtTokenProvider: JwtTokenProvider,
    private val kafkaTemplate: KafkaTemplate<String, Map<String, Any>>
) {

    @KafkaListener(topics = ["token-requests"], groupId = "group_id")
    fun validateToken(payload: Map<String, Any>) {
        val token = payload["token"] as String
        val postId = payload["postId"] as String // postId를 받아와 검증 요청을 추적합니다.

        // 토큰 검증
        try {
            jwtTokenProvider.getClaimsFromToken(token) // throws an exception if invalid

            // 검증 성공, 결과를 Kafka로 전송
            kafkaTemplate.send("token-responses", mapOf("postId" to postId, "isValid" to true))
        } catch (e: Exception) {
            // 검증 실패, 결과를 Kafka로 전송
            kafkaTemplate.send("token-responses", mapOf("postId" to postId, "isValid" to false))
        }
    }
}

