package com.sconnect.auth.controller

import com.sconnect.auth.service.VerificationService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class VerificationController(
    private val verificationService: VerificationService
) {

}