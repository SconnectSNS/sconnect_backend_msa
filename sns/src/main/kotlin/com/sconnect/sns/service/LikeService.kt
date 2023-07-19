package com.sconnect.sns.service

import com.sconnect.sns.client.AuthServiceClient
import com.sconnect.sns.model.dto.ResponseTokenDto
import com.sconnect.sns.model.entity.Like
import com.sconnect.sns.repository.LikeRepository
import org.springframework.stereotype.Service

@Service
class LikeService(
        private val likeRepository: LikeRepository,
        private val postService: PostService,
        private val authServiceClient: AuthServiceClient
) {
    fun createLike(postId: Long, jwt: String) {
        val userId = getUserInfoFromToken(jwt).userId
        val like = likeRepository.findByPostIdAndUserId(postId, userId.toLong())
        val post = postService.getValidatedPost(postId)
        likeRepository.save(Like(post = post, userId = userId.toLong()))
        if (like == null) {
            // 좋아요를 누른 적이 없는 경우
            increaseLikeCount(postId)
        } else {
            // 좋아요를 누른 적이 있는 경우
            decreaseLikeCount(postId)
        }
    }

    fun increaseLikeCount(postId: Long) {
        //일단 한건 발생할때마다 바로 db에 업데이트하고, 나중에 redis 이용방식으로 변경
        val post = postService.getValidatedPost(postId)
        post.likeCount += 1
        postService.savePost(post)
    }

    fun decreaseLikeCount(postId: Long) {
        //일단 한건 발생할때마다 바로 db에 업데이트하고, 나중에 redis 이용방식으로 변경
        val post = postService.getValidatedPost(postId)
        post.likeCount -= 1
        postService.savePost(post)
    }


    fun getUserInfoFromToken(authorization: String): ResponseTokenDto {
        //Feign을 이용해 auth서버에 요청
        return authServiceClient.getAccountInfo(authorization)

    }
}