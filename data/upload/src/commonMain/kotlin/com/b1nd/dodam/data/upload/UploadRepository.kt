package com.b1nd.dodam.data.upload

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.upload.model.UploadModel
import kotlinx.coroutines.flow.Flow

interface UploadRepository {

    suspend fun upload(fileName: String, fileMimeType: String, byteArray: ByteArray): Flow<Result<UploadModel>>
}