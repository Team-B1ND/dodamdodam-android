package com.b1nd.dodam.network.division.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.division.datasource.DivisionDataSource
import com.b1nd.dodam.network.division.response.DivisionOverviewResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class DivisionService constructor(
    private val httpClient: HttpClient
): DivisionDataSource {
    override suspend fun getAllDivisions(
        lastId: Int,
        limit: Int
    ): List<DivisionOverviewResponse> =
        safeRequest {
            httpClient.get(DodamUrl.DIVISION) {
                parameter("lastId", lastId)
                parameter("limit", limit)
            }.body<Response<List<DivisionOverviewResponse>>>()
        }

    override suspend fun getMyDivisions(
        lastId: Int,
        limit: Int
    ): List<DivisionOverviewResponse> =
        safeRequest {
            httpClient.get(DodamUrl.Division.MY) {
                parameter("lastId", lastId)
                parameter("limit", limit)
            }.body<Response<List<DivisionOverviewResponse>>>()
        }
}