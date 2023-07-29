package com.snapfolio.LogKafka.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.snapfolio.LogKafka.entity.UserTagUsage
import com.snapfolio.LogKafka.repository.UserTagUsageRepository
import com.snapfolio.LogKafka.request.UserTagUsageResponse
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class ScoreKafkaListener(
        private val userTagUsageRepository: UserTagUsageRepository
) {
    @KafkaListener(topics = ["user-tag-usage"], groupId = "group_id")
    fun handleValidationResult(message: String){
        val mapper = ObjectMapper()
        val data = mapper.readValue(message, UserTagUsageResponse::class.java)

        var userTagUsage = userTagUsageRepository.findByUserIdAndTagName(data.userId, data.tagName)

        if (userTagUsage != null) {
            userTagUsage.usageCount += 1
            userTagUsageRepository.save(userTagUsage)
        } else {
            userTagUsage = UserTagUsage(userId = data.userId, tagName = data.tagName, usageCount = 1)
            userTagUsageRepository.save(userTagUsage)
        }

        println("Updated user tag usage in database.")
    }
}
