package com.sconnect.sns.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Repository

@Repository
class RedisRepository(
        private val redisTemplate: RedisTemplate<Long, List<Long>>
) {
    private val valueOperations: ValueOperations<Long, List<Long>> by lazy {
        redisTemplate.opsForValue()
    }

    fun setRecommendedIds(results: Map<Long, List<Long>>) {
        results.forEach { (key, value) ->
            valueOperations.set(key, value)
        }
    }

    fun getRecommendedIds(postId: Long): List<Long>? {
        return valueOperations.get(postId) as? List<Long>
    }
}
