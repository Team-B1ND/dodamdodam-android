package com.b1nd.dodam.network.schedule.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleResponse(
    val id: Long,
    val name: String,
    val place: String?,
    val type: NetworkScheduleType,
    val date: List<LocalDate>,
    val targetGrades: List<NetworkGrade>,
)
