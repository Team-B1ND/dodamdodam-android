package com.b1nd.dodam.data.schedule.model

import com.b1nd.dodam.network.schedule.model.ScheduleResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDate

data class Schedule(
    val id: Long,
    val name: String,
    val place: String?,
    val type: ScheduleType,
    val date: ImmutableList<LocalDate>,
    val targetGrades: ImmutableList<Grade>,
)
internal fun ScheduleResponse.toModel(): Schedule = Schedule(
    id = id,
    name = name,
    place = place,
    type = type.toModel(),
    date = date.toImmutableList(),
    targetGrades = targetGrades.map { it.toModel() }.toImmutableList(),
)
