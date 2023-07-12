package com.sconnect.auth.model.dto

class ResponseTokenDto(
        val token: String,
        val postId: Long,
        val valid: Boolean,
        val userId: String
)