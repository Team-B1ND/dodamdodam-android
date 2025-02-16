package com.b1nd.dodam.data.notice.repository

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.notice.NoticeRepository
import com.b1nd.dodam.data.notice.model.Notice
import com.b1nd.dodam.data.notice.model.NoticeStatus
import com.b1nd.dodam.data.notice.model.toModel
import com.b1nd.dodam.network.notice.datasource.NoticeDataSource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NoticeRepositoryImpl(
    private val noticeDataSource: NoticeDataSource,
    private val dispatcher: CoroutineDispatcher,
): NoticeRepository {
    override suspend fun getNotice(
        keyword: String?,
        lastId: Int,
        limit: Int,
        status: NoticeStatus
    ): Flow<Result<ImmutableList<Notice>>> = flow {
        emit(
            noticeDataSource.getNotice(
                keyword = keyword,
                lastId = lastId,
                limit = limit,
                status = status.name,
            ).map {
                it.toModel()
            }
                .toImmutableList()
        )
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun getNoticeWithCategory(
        id: Int,
        lastId: Int,
        limit: Int
    ): Flow<Result<ImmutableList<Notice>>> = flow {
        emit(
            noticeDataSource.getNoticeWithCategory(
                id = id,
                lastId = lastId,
                limit = limit,
            ).map {
                it.toModel()
            }
                .toImmutableList()
        )
    }
        .flowOn(dispatcher)
        .asResult()
}