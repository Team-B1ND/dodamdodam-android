package com.b1nd.dodam.data.banner.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.banner.BannerRepository
import com.b1nd.dodam.data.banner.model.Banner
import com.b1nd.dodam.data.banner.model.toModel
import com.b1nd.dodam.network.banner.datasource.BannerDataSource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class BannerRepositoryImpl(
    private val bannerDataSource: BannerDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : BannerRepository {

    override fun getActiveBanner(): Flow<Result<ImmutableList<Banner>>> {
        return flow {
            emit(bannerDataSource.getActiveBanner().map { it.toModel() }.toImmutableList())
        }.asResult().flowOn(dispatcher)
    }
}
