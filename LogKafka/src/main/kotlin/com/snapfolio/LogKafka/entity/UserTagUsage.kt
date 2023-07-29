package com.snapfolio.LogKafka.entity

import javax.persistence.*

@Entity
@Table(name = "user_tag_usage")
data class UserTagUsage(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(name = "userId")
        val userId: Long,

        @Column(name = "tagName")
        val tagName: String,

        @Column(name = "usageCount")
        var usageCount: Int
)