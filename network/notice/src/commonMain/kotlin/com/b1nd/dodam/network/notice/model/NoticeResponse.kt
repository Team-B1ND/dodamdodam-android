package com.b1nd.dodam.network.notice.model

import com.b1nd.dodam.network.core.model.MemberResponse
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class NoticeResponse(
    val id: Int,
    val title: String,
    val content: String,
    val noticeFileRes: List<NoticeFileResponse>,
    val noticeStatus: NoticeStatusResponse,
    val memberInfoRes: MemberResponse,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
)
