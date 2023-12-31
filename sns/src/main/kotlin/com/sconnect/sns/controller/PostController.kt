package com.sconnect.sns.controller

import com.sconnect.sns.request.AccountRequest
import com.sconnect.sns.request.CreatePostRequest
import com.sconnect.sns.response.PostResponse
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
        val user = postService.getUserInfoFromToken(AccountRequest(authorization))
        val response = PostResponse(postService.createPost(authorization, createPostRequest), user)
        return ResponseEntity.ok().body(response)
    }

    // 게시글 목록 최신순 조회
    @GetMapping("posts")
    fun getPosts(
            @RequestParam("page") page: Int,
            @RequestParam("size") size: Int,
            @RequestParam(required = false) search: String?,
            @RequestParam(required = false, defaultValue = "createdAt") sort: String?,
    ): ResponseEntity<List<PostResponse>> {
        val posts = postService.getPosts(page, size, search, sort)
        val postResponses = mutableListOf<PostResponse>()
        posts.forEach {
            val user = it.userId?.let { it1 -> postService.getUserInfoFromId(it1) }
            postResponses.add(PostResponse(it, user))
        }
        return ResponseEntity.ok().body(postResponses)
    }

    // 게시글 상세 조회
    @GetMapping("posts/{postId}")
    fun getPost(
            @PathVariable postId: Long
    ): ResponseEntity<PostResponse> {
        val post = postService.getPost(postId)
        return ResponseEntity.ok().body(post)
    }


    //추천글 조회
    @GetMapping("posts/{postId}/recommend")
    fun getRecommendedPosts(
            @PathVariable postId: Long
    ): ResponseEntity<List<PostResponse>> {
        val posts = postService.getRecommendedPosts(postId)
        val postResponse: List<PostResponse> = posts.map {
            //각 게시물이 어떤 유저가 작성했는지 알아야함
            val user = postService.getUserInfoFromId(it.userId!!)
            PostResponse(it, user)
        }
        return ResponseEntity.ok().body(postResponse)
    }

}