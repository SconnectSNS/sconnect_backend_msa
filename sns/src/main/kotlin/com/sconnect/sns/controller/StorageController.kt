package com.sconnect.sns.controller

import com.sconnect.sns.service.StorageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/sns")
class StorageController(
        private val storageService: StorageService
) {
    @PostMapping("/upload")
    fun uploadFile(
            @RequestParam("file") file: MultipartFile
    ): ResponseEntity<*> {
        return ResponseEntity.ok(storageService.uploadFile(file))
    }
}