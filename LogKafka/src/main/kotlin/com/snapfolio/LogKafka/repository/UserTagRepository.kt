package com.snapfolio.LogKafka.repository

import com.snapfolio.LogKafka.entity.UserTagUsage
import org.springframework.data.jpa.repository.JpaRepository

interface UserTagUsageRepository : JpaRepository<UserTagUsage, Long> {
    fun findByUserIdAndTagName(userId: Long, tagName: String): UserTagUsage?
}