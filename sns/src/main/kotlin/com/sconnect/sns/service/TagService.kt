package com.sconnect.sns.service

import com.sconnect.sns.model.entity.Post
import com.sconnect.sns.model.entity.Tag
import com.sconnect.sns.repository.PostRepository
import com.sconnect.sns.repository.PostTagsRepository
import com.sconnect.sns.repository.TagRepository
import com.sconnect.sns.response.TagResponse
import org.springframework.stereotype.Service

@Service
class TagService(
        private val tagRepository: TagRepository,
        private val postTagsRepository: PostTagsRepository,
        private val postRepository: PostRepository
) {
    //image.imageData로부터 Tag 없으면 신규 생성
    fun createTag(imageData:String): List<Tag>{
        //imageData를 공백으로 split
        val tagList = mutableListOf<Tag>()
        val tags = imageData.split(" ")
        //tags를 하나씩 가져와서 tagRepository에 저장


        tags.forEach {
            //없는 경우에만 새로 생성
            val tag = if (!tagRepository.existsByTagName(it)) {
                val newTag = Tag(
                        tagName = it
                )
                tagRepository.save(newTag)
            } else {
                tagRepository.findByTagName(it)
            }
            tagList.add(tag)
        }

        println("tag1"+tags.get(0))
        return tagList
    }

    //postId로 Tag 조회
    fun getTagsByPostId(postId: Long): List<TagResponse> {
        val post = getValidatedPost(postId)
        val tags = postTagsRepository.findAllByPost(post)
        val tagNames = mutableListOf<TagResponse>()
        tags.forEach {
            tagNames.add(TagResponse(it.tag.tagName))
        }
        return tagNames
    }
    fun getValidatedPost(postId: Long): Post {
        return postRepository.findById(postId)
                .orElseThrow { throw IllegalArgumentException("Invalid post ID") }
    }
}