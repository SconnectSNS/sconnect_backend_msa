package com.sconnect.sns.controller

import com.sconnect.sns.response.TagResponse
import com.sconnect.sns.service.TagService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sns")
class TagController(
        private val tagService: TagService
) {
    @GetMapping("tags/{postId}")
    fun getTagsByPostId(@PathVariable postId: Long): ResponseEntity<List<TagResponse>> {
        return ResponseEntity.ok().body(tagService.getTagsByPostId(postId))
    }
}