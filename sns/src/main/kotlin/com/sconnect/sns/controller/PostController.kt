package com.sconnect.sns.controller

import com.sconnect.sns.request.CreatePostRequest
import com.sconnect.sns.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sns")
class PostController(

    private val postService: PostService
) {

    @PostMapping
    fun createPost(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody createPostRequest: CreatePostRequest
    ): ResponseEntity<Any> {


        postService.createPost(authorization, createPostRequest)

        return ResponseEntity.ok().body("Post creation in process.")
    }
}