package com.b1nd.dodam.network.schedule.model

import com.b1nd.dodam.network.core.model.NetworkPlace
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleResponse(
    val id: Long,
    val name: String,
    val place: NetworkPlace?,
    val type: NetworkScheduleType,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val targetGrades: List<NetworkGrade>,
)
