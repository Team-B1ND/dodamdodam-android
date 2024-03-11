package com.b1nd.dodam.network.schedule.datasource

import com.b1nd.dodam.network.schedule.model.ScheduleResponse
import kotlinx.collections.immutable.ImmutableList

interface ScheduleDataSource {
    suspend fun getScheduleBetweenPeriods(
        startDate: String,
        endDate: String,
    ): ImmutableList<ScheduleResponse>
}