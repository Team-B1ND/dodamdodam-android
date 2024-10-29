package com.b1nd.dodam.data.bundleidinfo

import com.b1nd.dodam.common.result.Result
import kotlinx.coroutines.flow.Flow

interface BundleIdInfoRepository {
    suspend fun getBundleId(): Flow<Result<String>>
}