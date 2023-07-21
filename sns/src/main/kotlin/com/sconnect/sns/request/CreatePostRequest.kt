package com.sconnect.sns.request

import javax.persistence.Id

class CreatePostRequest(
        val title: String = "",
        val content: String = "",
        val imageUrl: String = "",
        val imageId: Long = 0,
        val userId: Long? = null,
)