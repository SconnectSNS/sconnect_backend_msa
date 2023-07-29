package com.sconnect.sns.repository

import com.sconnect.sns.model.entity.Post
import com.sconnect.sns.model.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag,Long> {
    fun existsByTagName(tagName:String):Boolean
    fun findAllByPost(postId:Post):List<Tag>
}