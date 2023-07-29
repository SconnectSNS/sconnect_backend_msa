package com.snapfolio.LogKafka.request

data class UserTagUsageResponse(
        val userId: Long,
        val tagName: String
)