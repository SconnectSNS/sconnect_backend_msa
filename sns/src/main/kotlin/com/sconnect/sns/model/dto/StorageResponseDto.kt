package com.sconnect.sns.model.dto

data class StorageResponse(
        val imageUrls:String,
        val imageData:String
) {
    private val imageUrlList:String = imageUrls
}