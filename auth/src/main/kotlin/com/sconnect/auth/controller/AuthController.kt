package com.sconnect.auth.controller

import com.sconnect.auth.model.dto.SignInDto
import com.sconnect.auth.model.dto.SignUpDto
import com.sconnect.auth.request.AccountRequest
import com.sconnect.auth.request.SignInRequest
import com.sconnect.auth.request.SignUpRequest
import com.sconnect.auth.response.AccountResponse
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
    //TODO: ResponseStatus 안쓰고 responseentity로 변경하기

    // 회원가입
    @PostMapping("/signup")
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

    //msa 작동을 위한 유저 정보 리턴 api
    @PostMapping("/get-account-info")
    @ResponseStatus(HttpStatus.OK)
    fun getAccountInfo(
        @RequestBody token: AccountRequest
    ): AccountResponse {
        val accountInfo = authService.getAccountInfo(token.jwt)
        println("accountInfo: ${accountInfo.nickname}")
        return AccountResponse(accountInfo)
    }

    @PostMapping("/get-account-info-id")
    @ResponseStatus(HttpStatus.OK)
    fun getAccountInfoId(
        @RequestBody userId: Long
    ): AccountResponse {
        val accountInfo = authService.getAccountInfoId(userId)
        return AccountResponse(accountInfo)
    }

}