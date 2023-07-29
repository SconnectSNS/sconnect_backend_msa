package com.sconnect.sns.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class PostTags(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val postTagId: Long = 0,

        @OneToOne
        val post: Post,

        @OneToOne
        val tag: Tag
)