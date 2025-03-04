package com.b1nd.dodam.network.notice.model

import kotlinx.serialization.Serializable

@Serializable
enum class NoticeStatusResponse {
    CREATED,
    DRAFT,
    DELETED,
}
