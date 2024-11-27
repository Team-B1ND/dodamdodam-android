package com.b1nd.dodam.network.upload.model

import kotlinx.serialization.Serializable

@Serializable
data class UploadResponse(
    val status: Int,
    val message: String,
    val data: String,
)
