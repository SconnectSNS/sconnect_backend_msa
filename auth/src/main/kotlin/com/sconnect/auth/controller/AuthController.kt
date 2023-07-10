package com.sconnect.auth.controller

import com.sconnect.auth.model.dto.SignInDto
import com.sconnect.auth.model.dto.SignUpDto
import com.sconnect.auth.request.SignInRequest
import com.sconnect.auth.request.SignUpRequest
import com.sconnect.auth.response.SignInResponse
import com.sconnect.auth.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/users")
class AuthController(
    private val authService: AuthService
) {
    // 회원가입
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun signUp(
        @RequestBody @Valid
        request: SignUpRequest
    ){
        authService.signUp(
            SignUpDto(request.password, request.email, request.nickname)
        )
    }

    //로그인
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(
        @RequestBody @Valid
        request: SignInRequest
    ):SignInResponse{
        val token = authService.signIn(
            SignInDto(request.email, request.password)
        )
        return SignInResponse(token)
    }


}