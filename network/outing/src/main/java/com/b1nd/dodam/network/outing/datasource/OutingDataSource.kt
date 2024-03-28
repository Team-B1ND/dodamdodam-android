package com.b1nd.dodam.network.outing.datasource

import com.b1nd.dodam.network.outing.model.OutingResponse
import com.b1nd.dodam.network.outing.model.SleepoverResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

interface OutingDataSource {
    suspend fun getMySleepover(): ImmutableList<SleepoverResponse>
    suspend fun getMyOuting(): ImmutableList<OutingResponse>

    suspend fun askOuting(reason: String, startAt: LocalDateTime, endAt: LocalDateTime)

    suspend fun askSleepover(reason: String, startAt: LocalDate, endAt: LocalDate)
}
