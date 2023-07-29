package com.sconnect.sns.service

import com.sconnect.sns.client.RecommendationServiceClient
import com.sconnect.sns.repository.ImageRepository
import com.sconnect.sns.repository.RedisRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PictureRecommendationScheduledTask(
        private val imageRepository: ImageRepository,
        private val recommendationServiceClient: RecommendationServiceClient,
        private val redisRepository: RedisRepository
): ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        recommendation()
    }
    @Scheduled(fixedRate = 60 * 60 * 1000)
    fun recommendation() {
        println("RecommendationScheduledTask")
        // Image 데이터베이스에서 태그들을 읽어와서 추천 서버로 보냄
        val images = imageRepository.findAll()
        // python 서버로 http request 보냄
        val map = mutableMapOf<String, String>()
        for (image in images) {
            map[image.imageId.toString()] = image.imageData
        }
        val result = recommendationServiceClient.getEmbeddings(mapOf("keywords" to map))
        println(result.keys.toString())
        println(result.toString())
        // Save to Redis
        redisRepository.setRecommendedIds(
                result.mapKeys { it.key.toLong() }.mapValues { it.value.map { num -> num.toLong() } }
        )
    }
}