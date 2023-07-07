package com.sconnect.auth.repository

import com.sconnect.auth.model.entity.Verification
import org.springframework.data.jpa.repository.JpaRepository

interface VerificationRepository: JpaRepository<Verification, Long> {
}