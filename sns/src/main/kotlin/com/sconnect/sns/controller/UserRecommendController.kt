package com.sconnect.sns.controller

import com.sconnect.sns.response.AccountResponse
import com.sconnect.sns.service.UserRecommendService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sns")
class UserRecommendController(
        private val userRecommendService: UserRecommendService
) {
    //추천 친구 목록 조회
    @GetMapping("/user/{userId}/recommend")
    fun getRecommendUserIds(@PathVariable userId: Long): List<AccountResponse> {
        return userRecommendService.getRecommendUserIds(userId).map{
            AccountResponse(
                    accountId = it.accountId,
                    email = it.email,
                    nickname = it.nickname
            )
        }
    }
}