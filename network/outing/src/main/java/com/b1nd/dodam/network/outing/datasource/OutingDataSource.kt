package com.b1nd.dodam.network.outing.datasource

import com.b1nd.dodam.network.outing.model.OutingResponse
import com.b1nd.dodam.network.outing.model.SleepoverResponse
import kotlinx.collections.immutable.ImmutableList

interface OutingDataSource {
    suspend fun getMySleepover(): ImmutableList<SleepoverResponse>
    suspend fun getMyOuting(): ImmutableList<OutingResponse>
}
