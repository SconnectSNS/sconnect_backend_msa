package com.sconnect.sns.repository

import com.sconnect.sns.model.entity.PostTags
import org.springframework.data.jpa.repository.JpaRepository

interface PostTagsRepository:JpaRepository<PostTags,Long> {

}