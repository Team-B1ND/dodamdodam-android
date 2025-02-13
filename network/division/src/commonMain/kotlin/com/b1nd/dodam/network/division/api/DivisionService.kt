package com.b1nd.dodam.network.division.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.division.datasource.DivisionDataSource
import com.b1nd.dodam.network.division.response.DivisionInfoResponse
import com.b1nd.dodam.network.division.response.DivisionMemberCountResponse
import com.b1nd.dodam.network.division.response.DivisionMemberResponse
import com.b1nd.dodam.network.division.response.DivisionOverviewResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post

internal class DivisionService constructor(
    private val httpClient: HttpClient
): DivisionDataSource {
    override suspend fun getAllDivisions(
        lastId: Int,
        limit: Int,
        keyword: String,
    ): List<DivisionOverviewResponse> =
        safeRequest {
            httpClient.get(DodamUrl.DIVISION) {
                parameter("lastId", lastId)
                parameter("limit", limit)
                parameter("keyword", keyword)
            }.body<Response<List<DivisionOverviewResponse>>>()
        }

    override suspend fun getMyDivisions(
        lastId: Int,
        limit: Int,
        keyword: String,
    ): List<DivisionOverviewResponse> =
        safeRequest {
            httpClient.get(DodamUrl.Division.MY) {
                parameter("lastId", lastId)
                parameter("limit", limit)
                parameter("keyword", keyword)
            }.body<Response<List<DivisionOverviewResponse>>>()
        }

    override suspend fun getDivision(id: Int): DivisionInfoResponse =
        safeRequest {
            httpClient.get("${DodamUrl.DIVISION}/${id}")
                .body<Response<DivisionInfoResponse>>()
        }

    override suspend fun getDivisionMembers(id: Int, status: String): List<DivisionMemberResponse> =
        safeRequest {
            httpClient.get("${DodamUrl.DIVISION}/${id}/members") {
                parameter("status", status)
            }.body<Response<List<DivisionMemberResponse>>>()
        }

    override suspend fun getDivisionMembersCnt(
        id: Int,
        status: String
    ): Int =
        safeRequest {
            httpClient.get("${DodamUrl.DIVISION}/${id}/members/count") {
                parameter("status", status)
            }.body<Response<DivisionMemberCountResponse>>()
        }.count

    override suspend fun deleteDivisionMembers(divisionId: Int, memberId: List<Int>,) =
        defaultSafeRequest {
            httpClient.delete("${DodamUrl.DIVISION}/${divisionId}/members") {
                parameter("idList", memberId)
            }.body<DefaultResponse>()
        }

    override suspend fun postDivisionApplyRequest(divisionId: Int) =
        defaultSafeRequest {
            httpClient.post("${DodamUrl.DIVISION}/${divisionId}/members/apply")
                .body<DefaultResponse>()
        }
}