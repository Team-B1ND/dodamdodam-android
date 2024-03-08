package com.b1nd.dodam.network.nightstudy.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.nightstudy.datasource.NightStudyDataSource
import com.b1nd.dodam.network.nightstudy.model.NightStudyResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal class NightStudyService @Inject constructor(
    private val network: HttpClient,
) : NightStudyDataSource {
    override suspend fun getMyNightStudy(): ImmutableList<NightStudyResponse> {
        return safeRequest {
            network.get(DodamUrl.NightStudy.MY)
                .body<Response<List<NightStudyResponse>>>()
        }.toImmutableList()
    }
}