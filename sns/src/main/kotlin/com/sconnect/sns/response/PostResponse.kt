package com.sconnect.sns.response

import com.sconnect.sns.model.entity.Post
import java.time.LocalDateTime

class PostResponse(
    val postId: Long,
    val title: String,
    val content: String,
    val imageUrl: String,
    val userId: Long?,
    val imageData: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    constructor(post: Post) : this(
        postId = post.postId,
        title = post.title,
        content = post.content,
        imageUrl = post.imageUrl,
        userId = post.userId,
        imageData = post.imageData,
        createdAt = post.createdAt,
        updatedAt = post.updatedAt
    )
}