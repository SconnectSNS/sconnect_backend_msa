package com.snapfolio.LogKafka.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "userRecommendation", url = "http://localhost:5050")
interface UserRecommendationClient {
    @PostMapping("/userrecomendation")
    fun getUserRecommendation(userTagUsages: Map<String, List<String>>): Map<String, List<Int>>

}