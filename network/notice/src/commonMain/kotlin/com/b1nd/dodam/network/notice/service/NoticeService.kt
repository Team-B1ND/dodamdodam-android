package com.b1nd.dodam.network.notice.service

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.notice.datasource.NoticeDataSource
import com.b1nd.dodam.network.notice.model.NoticeFileRequest
import com.b1nd.dodam.network.notice.model.NoticeRequest
import com.b1nd.dodam.network.notice.model.NoticeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.contentType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class NoticeService(
    private val httpClient: HttpClient,
): NoticeDataSource {
    override suspend fun getNotice(keyword: String?, lastId: Int?, limit: Int, status: String): ImmutableList<NoticeResponse>
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
        lastId: Int?,
        limit: Int,
    ): ImmutableList<NoticeResponse>
    = safeRequest {
        httpClient.get("${DodamUrl.NOTICE}/division") {
            parameter("id", id)
            parameter("lastId", lastId)
            parameter("limit", limit)
        }.body<Response<List<NoticeResponse>>>()
    }.toImmutableList()

    override suspend fun postNoticeCreate(
        title: String,
        content: String,
        files: List<NoticeFileRequest>,
        divisions: List<Int>?
    ) = defaultSafeRequest {
        httpClient.post(DodamUrl.NOTICE) {
            setBody(
                NoticeRequest(
                    title = title,
                    content = content,
                    files = files,
                    divisions = divisions
                )
            )
        }.body<DefaultResponse>()
    }
}