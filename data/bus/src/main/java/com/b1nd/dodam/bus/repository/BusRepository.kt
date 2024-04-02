package com.b1nd.dodam.bus.repository

import com.b1nd.dodam.bus.model.Bus
import com.b1nd.dodam.common.result.Result
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface BusRepository {
    fun getBusList(): Flow<Result<ImmutableList<Bus>>>
    fun applyBus(id: Int): Flow<Result<Unit>>
    fun deleteBus(id: Int): Flow<Result<Unit>>
    fun updateBus(id: Int): Flow<Result<Unit>>
    fun getMyBus(): Flow<Result<Bus>>
}
