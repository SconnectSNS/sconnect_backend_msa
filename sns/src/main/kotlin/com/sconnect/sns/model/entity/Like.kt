package com.sconnect.sns.model.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
class Like(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @ManyToOne(fetch= FetchType.LAZY)
        @JsonBackReference
        var post: Post,

        var userId: Long = 0
)