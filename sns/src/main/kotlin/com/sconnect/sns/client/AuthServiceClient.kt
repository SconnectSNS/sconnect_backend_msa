package com.sconnect.sns.client

import com.sconnect.sns.model.dto.ResponseTokenDto
import com.sconnect.sns.request.AccountRequest
import com.sconnect.sns.response.AccountResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "auth")
interface AuthServiceClient {
    @PostMapping("/api/users/get-account-info")
    fun getAccountInfo(@RequestBody token: AccountRequest): AccountResponse

    @PostMapping("/api/users/get-account-info-id")
    fun getAccountInfoId(@RequestBody userId: Long): AccountResponse
}
