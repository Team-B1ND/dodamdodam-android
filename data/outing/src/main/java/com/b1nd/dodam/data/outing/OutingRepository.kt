package com.b1nd.dodam.data.outing

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.outing.model.Outing
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface OutingRepository {
    fun getMyOut(): Flow<Result<ImmutableList<Outing>>>
}
