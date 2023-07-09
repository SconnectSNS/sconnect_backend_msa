package com.sconnect.auth.exception

import com.sconnect.auth.response.ErrorResponse
import io.jsonwebtoken.security.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AuthExceptionHandler {
    companion object{
        //에러코드
        const val AUTH_001 = "Auth-001"
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleSignatureException(exception: SignatureException): ErrorResponse {
        return ErrorResponse(HttpStatus.UNAUTHORIZED, AUTH_001, "유효하지 않은 토큰입니다.")
    }

}