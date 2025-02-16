package com.b1nd.dodam.data.notice.model

import com.b1nd.dodam.data.core.model.Member
import com.b1nd.dodam.data.core.model.toModel
import com.b1nd.dodam.network.core.model.MemberResponse
import com.b1nd.dodam.network.notice.model.NoticeResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDateTime

data class Notice(
    val id: Int,
    val title: String,
    val content: String,
    val noticeFileRes: ImmutableList<NoticeFile>,
    val noticeStatus: NoticeStatus,
    val memberInfoRes: Member,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
)

internal fun NoticeResponse.toModel() =
    Notice(
        id = id,
        title = title,
        content = content,
        noticeFileRes = noticeFileRes.map {
            it.toModel()
        }.toImmutableList(),
        noticeStatus = noticeStatus.toModel(),
        memberInfoRes = memberInfoRes.toModel(),
        createdAt = createdAt,
        modifiedAt = modifiedAt,
    )
