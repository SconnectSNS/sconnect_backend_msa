package com.sconnect.sns.model.entity

import javax.persistence.*

@Entity
class PostTags(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val postTagId: Long = 0,

        @ManyToOne(cascade = [CascadeType.ALL])
        val post:Post,

        @ManyToOne
        val tag: Tag
)