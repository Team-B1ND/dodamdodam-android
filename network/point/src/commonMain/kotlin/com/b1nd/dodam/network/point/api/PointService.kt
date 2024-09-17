package com.b1nd.dodam.network.point.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.point.datasource.PointDataSource
import com.b1nd.dodam.network.point.model.PointReasonResponse
import com.b1nd.dodam.network.point.model.PointRequest
import com.b1nd.dodam.network.point.model.PointResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDate

internal class PointService(
    private val client: HttpClient,
) : PointDataSource {
    override suspend fun getMyPoint(type: String): ImmutableList<PointResponse> {
        return safeRequest {
            client.get(DodamUrl.Point.MY) {
                parameter("type", type)
            }.body<Response<List<PointResponse>>>()
        }.toImmutableList()
    }

    override suspend fun getScoreReason(type: String): ImmutableList<PointReasonResponse> {
        return safeRequest {
            client.get(DodamUrl.Point.REASON) {
                parameter("type", type)
            }.body<Response<List<PointReasonResponse>>>()
        }.toImmutableList()
    }

    override suspend fun postGivePoint(issueAt: LocalDate, reasonId: Int, studentIds: List<Int>) {
        return defaultSafeRequest {
            client.post(DodamUrl.POINT) {
                setBody(
                    PointRequest(
                        issueAt = issueAt,
                        reasonId = reasonId,
                        studentIds = studentIds,
                    ),
                )
                contentType(ContentType.Application.Json)
            }.body<DefaultResponse>()
        }
    }
}
