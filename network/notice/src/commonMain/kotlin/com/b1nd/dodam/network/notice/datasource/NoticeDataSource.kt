package com.b1nd.dodam.network.notice.datasource

import com.b1nd.dodam.network.notice.model.NoticeResponse
import kotlinx.collections.immutable.ImmutableList

interface NoticeDataSource {
    suspend fun getNotice(keyword: String?, lastId: Int, limit: Int, status: String): ImmutableList<NoticeResponse>
    suspend fun getNoticeWithCategory(id: Int, lastId: Int, limit: Int): ImmutableList<NoticeResponse>
}