package com.sconnect.sns.repository

import com.sconnect.sns.model.entity.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface PostRepository : JpaRepository<Post, Long> {
    fun findByTitle(title: String): Post?

    fun findAllByContentContaining(pageable: Pageable, content: String): Page<Post>
}