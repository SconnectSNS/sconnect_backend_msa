package com.sconnect.auth.model.entity

import javax.persistence.*

@Entity
@Table(name = "verifications")
class Verification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var email: String,
    var verificationCode: String,
    var sessionCode: String,
    var checked: Boolean = false
)