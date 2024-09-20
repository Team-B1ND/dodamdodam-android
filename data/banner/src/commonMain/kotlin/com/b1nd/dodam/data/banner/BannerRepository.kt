package com.b1nd.dodam.data.banner

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.banner.model.Banner
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface BannerRepository {
    fun getActiveBanner(): Flow<Result<ImmutableList<Banner>>>
}
