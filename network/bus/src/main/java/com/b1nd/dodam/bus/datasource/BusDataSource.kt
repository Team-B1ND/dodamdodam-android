package com.b1nd.dodam.bus.datasource

import com.b1nd.dodam.bus.model.BusResponse
import kotlinx.collections.immutable.ImmutableList

interface BusDataSource {
    suspend fun getBusList(): ImmutableList<BusResponse>
    suspend fun applyBus(id: Int)
    suspend fun deleteBus(id: Int)
    suspend fun updateBus(id: Int)
    suspend fun getMyBus(): BusResponse
}