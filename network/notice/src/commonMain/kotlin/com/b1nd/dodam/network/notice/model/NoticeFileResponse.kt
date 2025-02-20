package com.b1nd.dodam.network.notice.model

import kotlinx.serialization.Serializable

@Serializable
data class NoticeFileResponse(
    val fileName: String,
    val fileUrl: String,
    val fileType: NoticeFileTypeResponse,
)
