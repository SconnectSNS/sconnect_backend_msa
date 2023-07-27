package com.sconnect.sns.service

import com.sconnect.sns.client.RecommendationServiceClient
import com.sconnect.sns.repository.ImageRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RecommendationScheduledTask(
        private val imageRepository: ImageRepository,
        private val recommendationServiceClient: RecommendationServiceClient
) {
    @Scheduled(fixedRate = 60 * 60 * 1000) // 1시간마다
    fun recommendation() {
        //Image 데이터베이스에서 태그들을 읽어와서 추천 서버로 보냄
        val images = imageRepository.findAll()
        //python 서버로 http request 보냄
        val map = mutableMapOf<String, String>()
        for (image in images) {
            map[image.imageId.toString()] = image.imageData
        }
        val result = recommendationServiceClient.getEmbeddings(mapOf("keywords" to map))
        println("result: $result")
    }
}