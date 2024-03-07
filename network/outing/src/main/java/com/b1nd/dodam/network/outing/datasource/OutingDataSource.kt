package com.b1nd.dodam.network.outing.datasource

import com.b1nd.dodam.network.outing.model.OutingResponse
import kotlinx.collections.immutable.ImmutableList

interface OutingDataSource {
    suspend fun getMyOutSleeping(): ImmutableList<OutingResponse>
    suspend fun getMyOutGoing(): ImmutableList<OutingResponse>
}