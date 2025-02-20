package com.b1nd.dodam.data.notice.model

import com.b1nd.dodam.network.notice.model.NoticeStatusResponse

enum class NoticeStatus {
    CREATED,
    DRAFT,
    DELETED,
}

internal fun NoticeStatusResponse.toModel() = when (this) {
    NoticeStatusResponse.CREATED -> NoticeStatus.CREATED
    NoticeStatusResponse.DRAFT -> NoticeStatus.DRAFT
    NoticeStatusResponse.DELETED -> NoticeStatus.DELETED
}
