package com.sconnect.sns.repository

import com.sconnect.sns.model.entity.Post
import org.springframework.data.jpa.repository.JpaRepository


interface PostRepository: JpaRepository<Post, Long> {
    fun findByTitle(title: String): Post?
}