package com.sconnect.sns.service

import com.sconnect.sns.client.AuthServiceClient
import com.sconnect.sns.repository.RedisRepository
import com.sconnect.sns.response.AccountResponse
import org.springframework.stereotype.Service

@Service
class UserRecommendService(
        private val redisRepository: RedisRepository,
        private val authServiceClient: AuthServiceClient,
        private val postService: PostService
) {
    fun getRecommendUserIds(userId: Long): List<AccountResponse> {

        val accountList = mutableListOf<AccountResponse>()
        val users = redisRepository.getRecommendUserIds(userId)
        if (users.isNotEmpty()) {
            println(users.toString())
            users.forEach {
                println("user recommend: $it")
                val account = postService.getUserInfoFromId(it)
                accountList.add(account)
            }
            if (accountList.size == 0) {
                for (i in 1..3) {
                    val account = postService.getUserInfoFromId(i.toLong())
                    accountList.add(account)
                }
            }
        } else {
            println("random user recommend")
            //TODO: 일단 1,2,3번 유저 추천하도록 함
            for (i in 1..3) {
                val account = postService.getUserInfoFromId(i.toLong())
                accountList.add(account)
            }
        }

        return accountList
    }
}