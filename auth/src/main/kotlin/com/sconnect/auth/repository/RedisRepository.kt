package com.sconnect.auth.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisRepository {
    @Autowired
    private lateinit var redisTemplate: StringRedisTemplate

    fun saveRefreshToken(username: String, refreshToken: String) {
        redisTemplate.opsForValue().set(username, refreshToken)
    }

    fun findRefreshTokenByUsername(username: String): String? {
        return redisTemplate.opsForValue().get(username)
    }
}