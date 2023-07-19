package com.sconnect.sns.client

import com.sconnect.sns.model.dto.ResponseTokenDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "auth-service")
interface AuthServiceClient {
    @PostMapping("/get-account-info")
    fun getAccountInfo(@RequestBody token: String):ResponseTokenDto
}
