package com.sconnect.auth.repository

import com.sconnect.auth.model.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<Account, Long>{
    fun findByEmail(email: String): Account?
    fun existsByEmail(email: String): Boolean

    //TODO:
    /*
    fun findByEmailAndUserStatus(userId: String, userStatus: UserStatus): Account?
    fun findByUserIdAndRefreshToken(userId: String, refreshToken: String):Account?
     */
}