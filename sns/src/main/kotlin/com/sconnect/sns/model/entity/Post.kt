package com.sconnect.sns.model.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Post(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var postId: Long = 0,
        var title: String = "",
        var content: String = "",
        //User 추가
        // val user: User = User()
        var imageUrl: String = "",
        var userId: Long? = 0,
        @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var image: Image,
        var userName:String="",
        var likeCount: Int = 0,
        @OneToMany(mappedBy = "post")
        var likes: MutableList<Like> = mutableListOf(),
        @OneToMany(mappedBy = "post", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
        var postTags: MutableList<PostTags> = mutableListOf(),
        //Created At, Updated At 추가
        var createdAt: LocalDateTime = LocalDateTime.now(),
        var updatedAt: LocalDateTime = LocalDateTime.now()
)