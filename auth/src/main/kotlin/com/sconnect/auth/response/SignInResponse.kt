package com.sconnect.auth.response

import com.sconnect.auth.model.dto.AuthTokenDto

class SignInResponse(
    val accessToken: String,
    val accessTokenExpiredIn: Long,
    val refreshToken: String,
    val refreshTokenExpiredIn: Long
) {
    constructor(token: AuthTokenDto) :
        this(
            token.accessToken.token,
            token.accessToken.expiredIn,
            token.refreshToken!!.token,
            token.refreshToken!!.expiredIn
        )
}