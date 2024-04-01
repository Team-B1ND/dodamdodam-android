package com.b1nd.dodam.bus.api

import com.b1nd.dodam.bus.datasource.BusDataSource
import com.b1nd.dodam.bus.model.BusResponse
import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.network.core.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

internal class BusService @Inject constructor(
    private val client: HttpClient,
) : BusDataSource {
    override suspend fun getBusList(): ImmutableList<BusResponse> {
        return safeRequest {
            client.get(DodamUrl.BUS).body<Response<ImmutableList<BusResponse>>>()
        }
    }

    override suspend fun applyBus() {
        defaultSafeRequest {
            client.post(DodamUrl.BUS).body<DefaultResponse>()
        }
    }
}
