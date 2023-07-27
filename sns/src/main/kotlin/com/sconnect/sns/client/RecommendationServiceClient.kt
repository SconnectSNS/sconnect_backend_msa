package com.sconnect.sns.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "recommendation", url = "http://localhost:5000")
interface RecommendationServiceClient {

    @PostMapping("/embed")
    fun getEmbeddings(keywords: Map<String, Map<String, String>>): Map<String, List<Int>>
}