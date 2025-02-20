package com.b1nd.dodam.network.notice.model

import kotlinx.serialization.Serializable

@Serializable
data class NoticeFileRequest(
    val url: String,
    val name: String,
    val fileType: String,
)
