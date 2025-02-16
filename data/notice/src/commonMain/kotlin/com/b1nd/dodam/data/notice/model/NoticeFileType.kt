package com.b1nd.dodam.data.notice.model

import com.b1nd.dodam.network.notice.model.NoticeFileTypeResponse

enum class NoticeFileType {
    IMAGE, FILE
}

internal fun NoticeFileTypeResponse.toModel() =
    when (this) {
        NoticeFileTypeResponse.IMAGE -> NoticeFileType.IMAGE
        NoticeFileTypeResponse.FILE -> NoticeFileType.FILE
    }