package com.sconnect.sns.service

import com.sconnect.sns.model.entity.Tag
import com.sconnect.sns.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService(
        private val tagRepository: TagRepository
) {
    //image.imageData로부터 Tag 없으면 신규 생성
    fun createTag(imageData:String): List<Tag>{
        //imageData를 공백으로 split
        val tagList = mutableListOf<Tag>()
        val tags = imageData.split(" ")
        //tags를 하나씩 가져와서 tagRepository에 저장


        tags.forEach {
            //없는 경우에만 새로 생성
            if(!tagRepository.existsByTagName(it)){
                val newTag = Tag(
                        tagName = it
                )
                tagList.add(tagRepository.save(newTag))
            }
        }
        return tagList
    }
}