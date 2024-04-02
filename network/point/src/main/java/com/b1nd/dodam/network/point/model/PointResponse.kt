package com.b1nd.dodam.network.point.model

import com.b1nd.dodam.network.core.model.StudentResponse
import com.b1nd.dodam.network.core.model.TeacherResponse
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class PointResponse(
    val id: Int,
    val student: StudentResponse,
    val teacher: TeacherResponse,
    val reason: PointReasonResponse,
    val issueAt: LocalDate,
)
