package com.sconnect.auth.model.entity

import com.sconnect.auth.model.enums.UserStatus
import javax.persistence.*

@Entity
@Table(name = "accounts")
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long = 0,
    @Column(length = 64, unique = true)
    var email:String,
    val password:String,
    val nickname:String,
    val userStatus: UserStatus,
    //TODO:권한추가
){
    constructor(email: String, password: String, nickname: String) : this(
        email = email,
        password = password,
        nickname = nickname,
        userStatus = UserStatus.ACTIVE
    )
}