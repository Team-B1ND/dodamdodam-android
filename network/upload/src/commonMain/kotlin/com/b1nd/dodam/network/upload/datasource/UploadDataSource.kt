package com.b1nd.dodam.network.upload.datasource

interface UploadDataSource {

    suspend fun upload(fileName: String, fileMimeType: String, byteArray: ByteArray): String
}
