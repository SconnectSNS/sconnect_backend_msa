package com.sconnect.auth.model.dto

class AuthTokenDto(
    val accessToken: JwtTokenDto,
    val refreshToken: JwtTokenDto?
)