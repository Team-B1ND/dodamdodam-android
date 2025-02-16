package com.b1nd.dodam.data.notice.model

import com.b1nd.dodam.network.notice.model.NoticeFileResponse

data class NoticeFile(
    val fileUrl: String,
    val fileType: NoticeFileType
)

internal fun NoticeFileResponse.toModel() =
    NoticeFile(
        fileUrl = fileUrl,
        fileType = fileType.toModel(),
    )