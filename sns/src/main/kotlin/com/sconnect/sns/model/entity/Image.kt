package com.sconnect.sns.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class Image(
        //TODO: 어떤사람이 올린건지도 알 수 있도록 userId 저장 해야함
        @Id
        @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
        var imageId: Long = 0,
        var imageLink: String = "",
        var imageData: String = "",
        @OneToOne(mappedBy = "image")
        var post: Post? = null

){
        constructor(imageLink: String, imageData: String):this(){
                this.imageLink=imageLink
                this.imageData=imageData
        }
}