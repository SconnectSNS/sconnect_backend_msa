package com.sconnect.sns.model.entity

import javax.persistence.*

@Entity
class UserTagUsage(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val userTagUsageId: Long = 0,

        @Column(nullable = false)
        val userId: Long = 0,

        @OneToOne
        val tag: Tag,
)