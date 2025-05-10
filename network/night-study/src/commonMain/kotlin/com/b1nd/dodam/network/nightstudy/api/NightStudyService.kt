package com.b1nd.dodam.network.nightstudy.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.nightstudy.datasource.NightStudyDataSource
import com.b1nd.dodam.network.nightstudy.model.MyBanResponse
import com.b1nd.dodam.network.nightstudy.model.NightStudyRequest
import com.b1nd.dodam.network.nightstudy.model.NightStudyResponse
import com.b1nd.dodam.network.nightstudy.model.NightStudyStudentResponse
import com.b1nd.dodam.network.nightstudy.model.ProjectRequest
import com.b1nd.dodam.network.nightstudy.model.ProjectResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
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

    override suspend fun getPendingNightStudy(): ImmutableList<NightStudyResponse> {
        return safeRequest {
            network.get(DodamUrl.NightStudy.PENDING)
                .body<Response<List<NightStudyResponse>>>()
        }.toImmutableList()
    }

    override suspend fun getStudyingNightStudy(): ImmutableList<NightStudyResponse> {
        return safeRequest {
            network.get(DodamUrl.NIGHT_STUDY)
                .body<Response<List<NightStudyResponse>>>()
        }.toImmutableList()
    }

    override suspend fun askNightStudy(place: String, content: String, doNeedPhone: Boolean, reasonForPhone: String?, startAt: LocalDate, endAt: LocalDate) {
        return defaultSafeRequest {
            network.post(DodamUrl.NIGHT_STUDY) {
                contentType(ContentType.Application.Json)
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

    override suspend fun deleteProject(id: Long) {
        return defaultSafeRequest {
            network.delete(DodamUrl.PROJECT + "/$id")
                .body<DefaultResponse>()
        }
    }

    override suspend fun getNightStudy(): ImmutableList<NightStudyResponse> {
        return safeRequest {
            network.get(DodamUrl.NIGHT_STUDY)
                .body<Response<List<NightStudyResponse>>>()
        }.toImmutableList()
    }

    override suspend fun getNightStudyPending(): ImmutableList<NightStudyResponse> {
        return safeRequest {
            network.get(DodamUrl.NightStudy.PENDING)
                .body<Response<List<NightStudyResponse>>>()
        }.toImmutableList()
    }

    override suspend fun allowNightStudy(id: Long) {
        return defaultSafeRequest {
            network.patch(DodamUrl.NIGHT_STUDY + "/$id/allow")
                .body()
        }
    }

    override suspend fun rejectNightStudy(id: Long) {
        return defaultSafeRequest {
            network.patch(DodamUrl.NIGHT_STUDY + "/$id/reject")
                .body()
        }
    }

    override suspend fun askProjectStudy(
        type: String,
        name: String,
        description: String,
        startAt: LocalDate,
        endAt: LocalDate,
        room: String,
        students: List<Int>,
    ) {
        return defaultSafeRequest {
            network.post(DodamUrl.PROJECT) {
                contentType(ContentType.Application.Json)
                setBody(
                    ProjectRequest(
                        type,
                        name,
                        description,
                        startAt,
                        endAt,
                        room,
                        students,
                    ),
                )
            }.body<DefaultResponse>()
        }
    }

    override suspend fun myBan(): MyBanResponse {
        return safeRequest {
            network.get(DodamUrl.NightStudy.BAN)
                .body()
        }
    }

    override suspend fun getNightStudyStudent(): ImmutableList<NightStudyStudentResponse> {
        return safeRequest {
            network.get(DodamUrl.NightStudy.STUDENT)
                .body<Response<List<NightStudyStudentResponse>>>()
        }.toImmutableList()
    }

    override suspend fun getProject(): ImmutableList<ProjectResponse> {
        return safeRequest {
            network.get(DodamUrl.NightStudy.MYPROJECT)
                .body<Response<List<ProjectResponse>>>()
        }.toImmutableList()
    }
}
