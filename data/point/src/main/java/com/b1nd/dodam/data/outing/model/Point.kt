package com.b1nd.dodam.data.outing.model

import com.b1nd.dodam.data.core.model.Student
import com.b1nd.dodam.data.core.model.Teacher
import com.b1nd.dodam.data.core.model.toModel
import com.b1nd.dodam.network.core.model.StudentResponse
import com.b1nd.dodam.network.core.model.TeacherResponse
import com.b1nd.dodam.network.point.model.PointResponse
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

data class Point(
    val id: Int,
    val student: Student,
    val teacher: Teacher,
    val reason: PointReason,
    val issueAt: LocalDate,
)

internal fun PointResponse.toModel() = Point(
    id = id,
    student = student.toModel(),
    teacher = teacher.toModel(),
    reason = reason.toModel(),
    issueAt = issueAt
)
