package com.b1nd.dodam.network.outing.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.outing.datasource.OutingDataSource
import com.b1nd.dodam.network.outing.model.OutingRequest
import com.b1nd.dodam.network.outing.model.OutingResponse
import com.b1nd.dodam.network.outing.model.SleepoverRequest
import com.b1nd.dodam.network.outing.model.SleepoverResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

internal class OutingService(
    private val client: HttpClient,
) : OutingDataSource {
    override suspend fun getMySleepover(): ImmutableList<SleepoverResponse> {
        return safeRequest {
            client.get(DodamUrl.Sleepover.MY)
                .body<Response<List<SleepoverResponse>>>()
        }.toImmutableList()
    }

    override suspend fun getMyOuting(): ImmutableList<OutingResponse> {
        return safeRequest {
            client.get(DodamUrl.Outing.MY)
                .body<Response<List<OutingResponse>>>()
        }.toImmutableList()
    }

    override suspend fun askOuting(reason: String, startAt: LocalDateTime, endAt: LocalDateTime) {
        return defaultSafeRequest {
            client.post(DodamUrl.OUTING) {
                contentType(ContentType.Application.Json)
                setBody(OutingRequest(reason, startAt, endAt))
            }.body<DefaultResponse>()
        }
    }

    override suspend fun askSleepover(reason: String, startAt: LocalDate, endAt: LocalDate) {
        return defaultSafeRequest {
            client.post(DodamUrl.SLEEPOVER) {
                contentType(ContentType.Application.Json)
                setBody(SleepoverRequest(reason, startAt, endAt))
            }.body<DefaultResponse>()
        }
    }

    override suspend fun deleteOuting(id: Long) {
        return defaultSafeRequest {
            client.delete(DodamUrl.OUTING + "/$id")
                .body<DefaultResponse>()
        }
    }

    override suspend fun deleteSleepover(id: Long) {
        return defaultSafeRequest {
            client.delete(DodamUrl.SLEEPOVER + "/$id")
                .body<DefaultResponse>()
        }
    }
}
