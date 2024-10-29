package com.b1nd.dodam.data.bundleidinfo.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.bundleidinfo.BundleIdInfoRepository
import com.b1nd.dodam.data.core.model.toRequest
import com.seugi.network.bundleidinfo.datasource.BundleIdInfoDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BundleIdInfoRepositoryImpl(
    private val remote: BundleIdInfoDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher
) : BundleIdInfoRepository {
    override suspend fun getBundleId(): Flow<Result<String>> {
        return flow {
            emit(
                remote.getBundleId()?:"버전없음"
            )
        }.asResult().flowOn(dispatcher)
    }
}