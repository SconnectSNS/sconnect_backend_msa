package com.sconnect.sns.repository

import com.sconnect.sns.model.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag,Long> {
}