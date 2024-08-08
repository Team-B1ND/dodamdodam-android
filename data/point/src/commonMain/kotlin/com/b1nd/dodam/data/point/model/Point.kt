package com.b1nd.dodam.data.point.model

import com.b1nd.dodam.data.core.model.Student
import com.b1nd.dodam.data.core.model.Teacher
import com.b1nd.dodam.data.core.model.toModel
import com.b1nd.dodam.network.point.model.PointResponse
import kotlinx.datetime.LocalDate

data class Point(
    val id: Int,
    val student: Student,
    val teacher: Teacher,
    val reason: PointReason,
    val issueAt: LocalDate,
    val pointType: PointType,
)

internal fun PointResponse.toModel(pointType: PointType) = Point(
    id = id,
    student = student.toModel(),
    teacher = teacher.toModel(),
    reason = reason.toModel(),
    issueAt = issueAt,
    pointType = pointType,
)
