package com.b1nd.dodam.data.notice.model

import com.b1nd.dodam.network.notice.model.NoticeFileResponse
import kotlinx.serialization.Serializable

@Serializable
data class NoticeFile(
    val fileName: String,
    val fileUrl: String,
    val fileType: NoticeFileType,
)

internal fun NoticeFileResponse.toModel() = NoticeFile(
    fileName = fileName,
    fileUrl = fileUrl,
    fileType = fileType.toModel(),
)
