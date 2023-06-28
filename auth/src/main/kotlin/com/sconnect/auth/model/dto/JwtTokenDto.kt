package com.sconnect.auth.model.dto

class JwtTokenDto(
    val token: String,
    val expiredIn: Long
)