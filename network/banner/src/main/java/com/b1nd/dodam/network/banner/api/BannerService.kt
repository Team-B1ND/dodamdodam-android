package com.b1nd.dodam.network.banner.api

import com.b1nd.dodam.network.banner.datasource.BannerDataSource
import com.b1nd.dodam.network.banner.model.BannerResponse
import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

internal class BannerService @Inject constructor(
    private val client: HttpClient,
) : BannerDataSource {
    override suspend fun getActiveBanner(): ImmutableList<BannerResponse> {
        return safeRequest {
            client.get(DodamUrl.Banner.ACTIVE)
                .body<Response<List<BannerResponse>>>()
        }.toImmutableList()
    }
}
