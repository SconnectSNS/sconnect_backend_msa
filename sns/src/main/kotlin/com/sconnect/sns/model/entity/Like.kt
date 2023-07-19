package com.sconnect.sns.model.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Like(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var likeId: Long = 0,

        @ManyToOne(fetch = FetchType.LAZY, optional = true)
        @JoinColumn(name = "post_id")
        @JsonBackReference
        var post: Post? = null,



        var userId: Long = 0,

        var createdAt: LocalDateTime = LocalDateTime.now(),
        var updatedAt: LocalDateTime = LocalDateTime.now()
)