package com.b1nd.dodam.data.upload.model

import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.upload.model.UploadResponse

data class UploadModel(
    val profileImage: String
)

fun String.toModel() = UploadModel(
    profileImage = this
)