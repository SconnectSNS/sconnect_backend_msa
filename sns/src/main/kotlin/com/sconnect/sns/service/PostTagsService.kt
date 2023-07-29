package com.sconnect.sns.service

import com.sconnect.sns.model.entity.Post
import com.sconnect.sns.model.entity.PostTags
import com.sconnect.sns.model.entity.Tag
import com.sconnect.sns.repository.PostTagsRepository
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class PostTagsService(
        private val postTagsRepository: PostTagsRepository,
        private val kafkaTemplate: KafkaTemplate<String, String>,
) {
    //신규 생성 및 kafka 토픽으로 전송
    fun createPostTags(post: Post, tags: List<Tag>) {
        tags.forEach {
            val postTags = PostTags(
                    post = post,
                    tag = it
            )
            postTagsRepository.save(postTags)
        }
        //kafka 토픽으로 전송
        val jsonString = "{\"postId\":${post.postId}}"
        kafkaTemplate.send("post-tags", jsonString)
    }
}