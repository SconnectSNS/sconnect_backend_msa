package com.snapfolio.LogKafka.service

import com.snapfolio.LogKafka.client.UserRecommendationClient
import com.snapfolio.LogKafka.repository.UserTagUsageRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class UserRecommendationScheduledTask(
        private val userTagUsageRepository: UserTagUsageRepository,
        private val userRecommendationClient: UserRecommendationClient
): ApplicationRunner{
    override fun run(args: ApplicationArguments?) {
        recommendation()
    }

    @Scheduled(fixedRate = 60 * 60 * 1000) // 1시간마다 실행
    fun recommendation(){
        println("UserRecommendationScheduledTask")
        // UserTagUsage 데이터베이스에서 사용자별 태그 사용량을 읽어와서 추천 서버로 보냄
        val userTagUsages = userTagUsageRepository.findAll()
        // python 서버로 http request 보냄
        val map = mutableMapOf<String, List<String>>()
        for(userTagUsage in userTagUsages){
            map[userTagUsage.userId.toString()] = listOf(userTagUsage.tagName, userTagUsage.usageCount.toString())
        }
        //openfeign으로 python 서버로 http request 보냄
        val result = userRecommendationClient.getUserRecommendation(map)
        // Save to Redis
        // redisRepository.setRecommendedIds(
        //         result.mapKeys { it.key.toLong() }.mapValues { it.value.map { num -> num.toLong() } }
        // )
    }

}