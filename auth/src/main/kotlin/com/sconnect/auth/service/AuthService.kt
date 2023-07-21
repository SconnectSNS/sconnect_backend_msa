package com.sconnect.auth.service

import com.sconnect.auth.exception.CantSignInException
import com.sconnect.auth.model.dto.AuthTokenDto
import com.sconnect.auth.model.dto.SignInDto
import com.sconnect.auth.model.dto.SignUpDto
import com.sconnect.auth.model.entity.Account
import com.sconnect.auth.repository.AccountRepository
import com.sconnect.auth.repository.RedisRepository
import com.sconnect.auth.security.JwtTokenProvider
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit

@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val accountRepository: AccountRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val redisRepository: RedisRepository
) {
    @Transactional(rollbackFor = [Exception::class])
    fun signUp(dto:SignUpDto) {
        accountRepository.save(
            Account(
                email = dto.email,
                password = passwordEncoder.encode(dto.password),
                nickname = dto.nickname
            )
        )
    }

    @Transactional
    fun signIn(dto:SignInDto): AuthTokenDto{
        val account = accountRepository.findByEmail(dto.email)?: throw CantSignInException()
        if(!passwordEncoder.matches(dto.password, account.password)) throw CantSignInException()

        val refreshToken = jwtTokenProvider.generateRefreshToken(account.email)

        redisRepository.saveRefreshToken(account.email, refreshToken.token)

        return AuthTokenDto(jwtTokenProvider.generateAccessToken(account.email), refreshToken)
    }

    fun getAccountInfo(token: String): Account {
        val email = jwtTokenProvider.getUserEmailFromToken(token)
        return accountRepository.findByEmail(email)?: throw CantSignInException()
    }
}