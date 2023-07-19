package com.sconnect.sns.repository

import com.sconnect.sns.model.entity.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository: JpaRepository<Image,Long> {

}