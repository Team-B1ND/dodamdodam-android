package com.b1nd.dodam.network.outing.datasource

import com.b1nd.dodam.network.outing.model.OutingResponse
import kotlinx.collections.immutable.ImmutableList

interface OutingDataSource {
    suspend fun getMySleepover(): ImmutableList<OutingResponse>
    suspend fun getMyOuting(): ImmutableList<OutingResponse>
}
