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
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

internal class BusService(
    private val client: HttpClient,
) : BusDataSource {
    override suspend fun getBusList(): ImmutableList<BusResponse> {
        return safeRequest {
            client.get(DodamUrl.BUS).body<Response<List<BusResponse>>>()
        }.toImmutableList()
    }

    override suspend fun applyBus(id: Int) {
        defaultSafeRequest {
            client.post(DodamUrl.Bus.APPLY + "/$id").body<DefaultResponse>()
        }
    }

    override suspend fun deleteBus(id: Int) {
        defaultSafeRequest {
            client.delete(DodamUrl.Bus.APPLY + "/$id").body<DefaultResponse>()
        }
    }

    override suspend fun updateBus(id: Int) {
        defaultSafeRequest {
            client.patch(DodamUrl.Bus.APPLY + "/$id").body<DefaultResponse>()
        }
    }

    override suspend fun getMyBus(): BusResponse {
        return safeRequest {
            client.get(DodamUrl.Bus.APPLY).body<Response<BusResponse>>()
        }
    }
}
