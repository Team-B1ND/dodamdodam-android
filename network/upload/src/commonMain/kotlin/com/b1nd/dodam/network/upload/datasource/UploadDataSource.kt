package com.b1nd.dodam.network.upload.datasource

import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.upload.model.UploadResponse

interface UploadDataSource {

    suspend fun upload(fileName: String, fileMimeType: String, byteArray: ByteArray): String
}