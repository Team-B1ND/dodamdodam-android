package com.b1nd.dodam.network.notice.service

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.notice.datasource.NoticeDataSource
import com.b1nd.dodam.network.notice.model.NoticeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class NoticeService(
    private val httpClient: HttpClient,
): NoticeDataSource {
    override suspend fun getNotice(keyword: String?, lastId: Int, limit: Int, status: String): ImmutableList<NoticeResponse>
    = safeRequest {
        httpClient.get(DodamUrl.NOTICE) {
            parameter("keyword", keyword?: "")
            parameter("lastId", lastId)
            parameter("limit", limit)
            parameter("status", status)
        }.body<Response<List<NoticeResponse>>>()
    }.toImmutableList()

    override suspend fun getNoticeWithCategory(
        id: Int,
        lastId: Int,
        limit: Int,
    ): ImmutableList<NoticeResponse>
    = safeRequest {
        httpClient.get("${DodamUrl.NOTICE}/${id}/division") {
            parameter("id", id)
            parameter("lastId", lastId)
            parameter("limit", limit)
        }.body<Response<List<NoticeResponse>>>()
    }.toImmutableList()
}