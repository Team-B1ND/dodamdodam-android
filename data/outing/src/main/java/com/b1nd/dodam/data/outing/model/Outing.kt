package com.b1nd.dodam.data.outing.model

import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.core.model.Student
import com.b1nd.dodam.data.core.model.toModel
import com.b1nd.dodam.network.outing.model.OutingResponse
import com.b1nd.dodam.network.outing.model.SleepoverResponse
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime

data class Outing(
    val id: Long,
    val outType: OutType,
    val reason: String,
    val status: Status,
    val student: Student,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val rejectReason: String?,
)

enum class OutType {
    OUTING,
    SLEEPOVER,
}

internal fun OutingResponse.toModel(): Outing = Outing(
    id = id,
    outType = OutType.OUTING,
    reason = reason,
    status = status.toModel(),
    student = student.toModel(),
    startAt = startAt,
    endAt = endAt,
    createdAt = createdAt,
    modifiedAt = modifiedAt,
    rejectReason = rejectReason,
)

internal fun SleepoverResponse.toModel(): Outing = Outing(
    id = id,
    outType = OutType.SLEEPOVER,
    reason = reason,
    status = status.toModel(),
    student = student.toModel(),
    startAt = startAt.atTime(21, 0, 0),
    endAt = endAt.atTime(21, 0, 0),
    createdAt = createdAt,
    modifiedAt = modifiedAt,
    rejectReason = rejectReason,
)
