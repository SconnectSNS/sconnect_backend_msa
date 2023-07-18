package com.sconnect.sns.service
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.PutObjectRequest
import com.sconnect.sns.model.dto.StorageResponse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
@Service
class StorageService(
        @Autowired
        private val s3Client: AmazonS3,
        @Value("\${application.bucket.name}")
        private val bucketName: String
) {


    fun uploadFile(files: List<MultipartFile>): StorageResponse {
        val imageUrls = mutableListOf<String>()

        for (file in files) {
            val fileObj: File = convertMultiPartFileToFile(file)
            val fileName = System.currentTimeMillis().toString() + "_" + file.originalFilename
            s3Client.putObject(PutObjectRequest(bucketName, fileName, fileObj))
            imageUrls.add(s3Client.getUrl(bucketName, fileName).toString())
            fileObj.delete()
        }
        return StorageResponse(imageUrls.joinToString(","))
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

}