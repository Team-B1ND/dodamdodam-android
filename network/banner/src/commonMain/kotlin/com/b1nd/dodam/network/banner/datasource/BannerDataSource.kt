package com.b1nd.dodam.network.banner.datasource

import com.b1nd.dodam.network.banner.model.BannerResponse
import kotlinx.collections.immutable.ImmutableList

interface BannerDataSource {
    suspend fun getActiveBanner(): ImmutableList<BannerResponse>
}
