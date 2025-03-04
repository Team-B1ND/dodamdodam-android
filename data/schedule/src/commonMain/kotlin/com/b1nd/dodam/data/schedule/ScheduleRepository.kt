package com.b1nd.dodam.data.schedule

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.schedule.model.Schedule
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface ScheduleRepository {
    fun getScheduleBetweenPeriods(startAt: LocalDate, endAt: LocalDate): Flow<Result<ImmutableList<Schedule>>>
}
