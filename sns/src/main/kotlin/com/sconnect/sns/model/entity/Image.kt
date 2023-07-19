package com.sconnect.sns.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Image(
        @Id
        @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
        var imageId: Long = 0,
        var imageLink: String = "",
        var imageData: String = "",

){
        constructor(imageLink: String, imageData: String):this(){
                this.imageLink=imageLink
                this.imageData=imageData
        }
}