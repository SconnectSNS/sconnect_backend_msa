package com.sconnect.sns.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.PutObjectRequest
import com.google.cloud.Identity
import com.google.cloud.vision.v1.*
import com.google.rpc.context.AttributeContext
import com.sconnect.sns.model.dto.StorageResponse
import com.sconnect.sns.repository.ImageRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gcp.vision.CloudVisionTemplate
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString

@Service
class StorageService(
        @Autowired
        private val s3Client: AmazonS3,
        @Value("\${application.bucket.name}")
        private val bucketName: String,
        @Autowired
        private val cloudVisionTemplate: CloudVisionTemplate,
        @Autowired
        private val resourceLoader: ResourceLoader,
        private val imageRepository: ImageRepository
) {


    fun uploadFile(
            file: MultipartFile
    ): StorageResponse {
        val fileObj: File = convertMultiPartFileToFile(file)
        val fileName = System.currentTimeMillis().toString() + "_" + file.originalFilename
        s3Client.putObject(PutObjectRequest(bucketName, fileName, fileObj))
        val imageUrl = s3Client.getUrl(bucketName, fileName).toString()
        fileObj.delete()
        val lableString:String = getGoogleApiResult(imageUrl)
        //save imageInfos to DB
        imageRepository.save(com.sconnect.sns.model.entity.Image(imageUrl, lableString))

        //TODO: response 바꾸기
        return StorageResponse(imageUrl, lableString)
    }

    private fun convertMultiPartFileToFile(file: MultipartFile): File {
        val convertedFile = File(file.originalFilename)
        try {
            FileOutputStream(convertedFile).use { fos -> fos.write(file.bytes) }
        } catch (e: Exception) {
            //오류 throw
        }
        return convertedFile
    }

    //google api에 값 요청하는 부분
    fun getGoogleApiResult(file: String) : String{
        val response: AnnotateImageResponse = cloudVisionTemplate.analyzeImage(
                resourceLoader.getResource(file),
                Feature.Type.LABEL_DETECTION
        )
        val labels: Map<String,Float> = response.labelAnnotationsList.map { it.description to it.score }.toMap()
        println(labels.keys.joinToString(" "))
        return labels.keys.joinToString(" ")
    }

}