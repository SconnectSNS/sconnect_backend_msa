package com.sconnect.sns.repository

import com.sconnect.sns.model.entity.Like
import org.springframework.data.jpa.repository.JpaRepository


interface LikeRepository:JpaRepository<Like,Long> {
}