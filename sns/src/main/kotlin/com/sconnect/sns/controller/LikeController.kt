package com.sconnect.sns.controller

import com.sconnect.sns.service.LikeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sns")
class LikeController(
        private val likeService: LikeService
) {
    @PostMapping("/like")
    fun createLike(
            @RequestParam("postId") postId: Long,
            @RequestHeader("Authorization") authorization: String
    ){
        val likeResponse = likeService.createLike(postId, authorization)
        return ResponseEntity.ok().body(likeResponse)
    }
}