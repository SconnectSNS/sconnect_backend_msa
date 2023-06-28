package com.sconnect.auth.model.dto

import javax.validation.constraints.Pattern

class SignUpDto(
    @field:Pattern(regexp = PASSWORD_REGEX, message = "유효하지 않은 비밀번호입니다.")
    val password:String,
    val email:String,
    @field:Pattern(regexp = NICKNAME_REGEX, message = "유효하지 않은 닉네임입니다.")
    val nickname:String,
) {
    companion object{
        const val PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[!-~₩]{8,100}$"
        const val NICKNAME_REGEX = "[A-Za-z|가-힣|ㄱ-ㅎ|ㅏ-ㅣ\\d\\s]{2,12}\$"
    }
}