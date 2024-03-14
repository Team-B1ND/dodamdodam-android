package com.b1nd.dodam.data.schedule.model

import com.b1nd.dodam.data.core.model.Place
import com.b1nd.dodam.data.core.model.toModel
import com.b1nd.dodam.network.schedule.model.ScheduleResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDate

data class Schedule(
    val id: Long,
    val name: String,
    val place: Place?,
    val type: ScheduleType,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val targetGrades: ImmutableList<Grade>,
)
internal fun ScheduleResponse.toModel(): Schedule = Schedule(
    id = id,
    name = name,
    place = place?.toModel(),
    type = type.toModel(),
    startDate = startDate,
    endDate = endDate,
    targetGrades = targetGrades.map { it.toModel() }.toImmutableList(),
)
