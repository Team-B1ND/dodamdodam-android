package com.b1nd.dodam.network.nightstudy.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.nightstudy.datasource.NightStudyDataSource
import com.b1nd.dodam.network.nightstudy.model.NightStudyRequest
import com.b1nd.dodam.network.nightstudy.model.NightStudyResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDate

internal class NightStudyService(
    private val network: HttpClient,
) : NightStudyDataSource {
    override suspend fun getMyNightStudy(): ImmutableList<NightStudyResponse> {
        return safeRequest {
            network.get(DodamUrl.NightStudy.MY)
                .body<Response<List<NightStudyResponse>>>()
        }.toImmutableList()
    }

    override suspend fun askNightStudy(place: String, content: String, doNeedPhone: Boolean, reasonForPhone: String?, startAt: LocalDate, endAt: LocalDate) {
        return defaultSafeRequest {
            network.post(DodamUrl.NIGHT_STUDY) {
                setBody(
                    NightStudyRequest(
                        place,
                        content,
                        doNeedPhone,
                        reasonForPhone,
                        startAt,
                        endAt,
                    ),
                )
            }.body<DefaultResponse>()
        }
    }

    override suspend fun deleteNightStudy(id: Long) {
        return defaultSafeRequest {
            network.delete(DodamUrl.NIGHT_STUDY + "/$id")
                .body<DefaultResponse>()
        }
    }
}
