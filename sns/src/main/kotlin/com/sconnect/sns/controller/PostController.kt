package com.sconnect.sns.controller

import com.sconnect.sns.request.CreatePostRequest
import com.sconnect.sns.service.PostService
import org.springframework.http.ResponseEntity
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

    // 게시글 목록 최신순 조회
    @GetMapping("posts")
    fun getPosts(
            @RequestParam("page") page: Int,
            @RequestParam("size") size: Int,
            @RequestParam(required = false) search: String?,
            @RequestParam(required = false, defaultValue = "createdAt") sort: String?,
    ): ResponseEntity<Any> {
        val posts = postService.getPosts(page, size, sort, search)

        return ResponseEntity.ok().body(posts)
    }

    // 게시글 상세 조회
    @GetMapping("posts/{postId}")
    fun getPost(@PathVariable postId: Long): ResponseEntity<Any> {
        val post = postService.getPost(postId)

        return ResponseEntity.ok().body(post)
    }
}