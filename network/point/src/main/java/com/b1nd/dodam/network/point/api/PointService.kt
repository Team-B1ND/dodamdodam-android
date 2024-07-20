package com.b1nd.dodam.network.point.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.point.datasource.PointDataSource
import com.b1nd.dodam.network.point.model.PointResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

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
}
