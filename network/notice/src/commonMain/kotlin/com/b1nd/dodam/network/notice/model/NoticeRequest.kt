package com.b1nd.dodam.network.notice.model

import kotlinx.serialization.Serializable

@Serializable
data class NoticeRequest(
    val title: String,
    val content: String,
    val files: List<NoticeFileRequest>,
    val divisions: List<Int>?,
)
