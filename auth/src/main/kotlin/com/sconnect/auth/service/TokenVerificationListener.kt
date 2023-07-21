package com.sconnect.auth.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sconnect.auth.model.dto.RequestTokenDto
import com.sconnect.auth.model.dto.ResponseTokenDto
import com.sconnect.auth.repository.AccountRepository
import com.sconnect.auth.security.JwtTokenProvider
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class TokenValidationListener(
        private val jwtTokenProvider: JwtTokenProvider,
        private val kafkaTemplate: KafkaTemplate<String, String>,
        private val accountRepository: AccountRepository
) {

    @KafkaListener(topics = ["token-request"], groupId = "group_id")
    fun validateToken(payload: String) {
        val mapper = jacksonObjectMapper()
        val requestToken: RequestTokenDto = mapper.readValue(payload, RequestTokenDto::class.java)

        val token = requestToken.token
        val postId = requestToken.postId

        // 토큰 검증
        try {
            // 검증 성공
            // jwt에 해당하는 userId를 가져오기
            //jwt에서는 email이 들어있음
            val email = jwtTokenProvider.getUserEmailFromToken(token)
            val userId = accountRepository.findByEmail(email)?.accountId.toString()
            val nickname = accountRepository.findByEmail(email)?.nickname.toString()
            // 결과를 Kafka로 전송
            val responseToken = ResponseTokenDto(token, postId, true, userId, nickname)
            val jsonString = mapper.writeValueAsString(responseToken)
            kafkaTemplate.send("token-response", jsonString)
        } catch (e: Exception) {
            // 검증 실패, 결과를 Kafka로 전송
            val responseToken = ResponseTokenDto(token, postId, false, "", "null")
            val jsonString = mapper.writeValueAsString(responseToken)
            kafkaTemplate.send("token-response", jsonString)
        }
    }
}

