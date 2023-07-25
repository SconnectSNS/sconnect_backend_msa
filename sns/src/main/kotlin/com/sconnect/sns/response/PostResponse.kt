package com.sconnect.sns.response

import com.sconnect.sns.model.entity.Post
import com.sconnect.sns.request.AccountRequest
import java.time.LocalDateTime

class PostResponse(
    val postId: Long,
    val title: String,
    val content: String,
    val imageUrl: String,
    val user: AccountResponse?,
    val imageData: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    constructor(post: Post, user:AccountResponse?) : this(
        postId = post.postId,
        title = post.title,
        content = post.content,
        imageUrl = post.imageUrl,
        user = user,
        imageData = post.image.imageData,
        createdAt = post.createdAt,
        updatedAt = post.updatedAt
    )
}