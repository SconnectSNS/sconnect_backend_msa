package com.sconnect.sns.model.entity

import javax.persistence.*

@Entity
class Tag(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val tagId: Long = 0,
        @Column(nullable = false, unique = true)
        val tagName: String = "",
        @OneToMany(mappedBy = "tag")
        val posts: List<Post> = listOf()
){
        constructor(
                name: String
        ): this(
                tagName = name,
                posts = listOf()
        )
}