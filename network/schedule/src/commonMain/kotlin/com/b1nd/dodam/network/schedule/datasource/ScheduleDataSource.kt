package com.b1nd.dodam.network.schedule.datasource

import com.b1nd.dodam.network.schedule.model.ScheduleResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate

interface ScheduleDataSource {
    suspend fun getScheduleBetweenPeriods(startAt: LocalDate, endAt: LocalDate): ImmutableList<ScheduleResponse>
}
