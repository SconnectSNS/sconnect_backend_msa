package com.sconnect.sns.service

import com.sconnect.sns.model.entity.Like
import com.sconnect.sns.repository.LikeRepository
import org.springframework.stereotype.Service

@Service
class LikeService(
        private val likeRepository: LikeRepository,
        private val postService: PostService
) {
    fun createLike(postId: Long, authorization: String) {
        val userId =
        val like = likeRepository.findByPostIdAndUserId(postId, userId)
        if (like == null) {
            // 좋아요를 누른 적이 없는 경우
            likeRepository.save(Like(postId = postId, userId = userId))
            postService.increaseLikeCount(postId)
        } else {
            // 좋아요를 누른 적이 있는 경우
            likeRepository.delete(like)
            postService.decreaseLikeCount(postId)
        }
    }
}