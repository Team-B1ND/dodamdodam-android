package com.b1nd.dodam.data.notice

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.notice.model.Notice
import com.b1nd.dodam.data.notice.model.NoticeFile
import com.b1nd.dodam.data.notice.model.NoticeStatus
import com.b1nd.dodam.network.notice.model.NoticeFileRequest
import com.b1nd.dodam.network.notice.model.NoticeResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {
    suspend fun getNotice(keyword: String?, lastId: Int?, limit: Int, status: NoticeStatus): Flow<Result<ImmutableList<Notice>>>
    suspend fun getNoticeWithCategory(id: Int, lastId: Int?, limit: Int): Flow<Result<ImmutableList<Notice>>>
    suspend fun postNoticeCreate(title: String, content: String, files: List<NoticeFile>, divisions: List<Int>?): Flow<Result<Unit>>
}