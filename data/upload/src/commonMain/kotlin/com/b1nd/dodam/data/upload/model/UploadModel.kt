package com.b1nd.dodam.data.upload.model

data class UploadModel(
    val profileImage: String,
)

fun String.toModel() = UploadModel(
    profileImage = this,
)
