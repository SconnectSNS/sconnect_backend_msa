package com.sconnect.sns.model.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Post(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        var title: String = "",
        var content: String = "",
        //User 추가
        // val user: User = User()
        var imageUrl: String = "",
        var userId: Long? = 0,
        var imageData:String?="",
        var userName:String="",

        //Created At, Updated At 추가
        var createdAt: LocalDateTime = LocalDateTime.now(),
        var updatedAt: LocalDateTime = LocalDateTime.now()
)