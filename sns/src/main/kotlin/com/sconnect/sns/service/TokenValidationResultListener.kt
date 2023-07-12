package com.sconnect.sns.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sconnect.sns.model.dto.ResponseTokenDto
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class TokenValidationResultListener(
    private val postService: PostService
) {
    //게시글 작성 요청에 대한 검증 결과를 받는 메서드
    @KafkaListener(topics = ["token-response"], groupId = "group_id")
    fun handleValidationResult(payload: String) {
        val mapper = jacksonObjectMapper()
        val responseToken: ResponseTokenDto = mapper.readValue(payload, ResponseTokenDto::class.java)
        if(responseToken.valid) {
            //검증 성공
            postService.completePost(responseToken.postId, responseToken.userId)
        } else {
            //검증 실패
            postService.rollbackPost(responseToken.postId)
        }
    }
}


