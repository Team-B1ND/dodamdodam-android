package com.b1nd.dodam.network.outing.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.outing.datasource.OutingDataSource
import com.b1nd.dodam.network.outing.model.OutingResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

internal class OutingService @Inject constructor(
    private val client: HttpClient,
) : OutingDataSource {
    override suspend fun getMySleepover(): ImmutableList<OutingResponse> {
        return safeRequest {
            client.get(DodamUrl.Sleepover.MY)
                .body<Response<List<OutingResponse>>>()
        }.toImmutableList()
    }

    override suspend fun getMyOuting(): ImmutableList<OutingResponse> {
        return safeRequest {
            client.get(DodamUrl.Outing.MY)
                .body<Response<List<OutingResponse>>>()
        }.toImmutableList()
    }
}
