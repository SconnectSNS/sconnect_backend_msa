package com.sconnect.sns.model.dto

class ResponseTokenDto(
        val token: String,
        val postId: Long,
        val valid: Boolean,
        val userId: String,
        val nickname: String,
) {
    constructor(token: String, postId: Long, valid: Boolean, userId: String) : this(
            token = token,
            postId = postId,
            valid = valid,
            userId = userId,
            nickname = ""
    )
}