package com.b1nd.dodam.data.outing.model

import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.core.model.Student
import com.b1nd.dodam.data.core.model.toModel
import com.b1nd.dodam.network.outing.model.OutingResponse
import kotlinx.datetime.LocalDateTime

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
)

enum class OutType {
    OUTING,
    SLEEPOVER,
}

internal fun OutingResponse.toModel(outType: OutType): Outing = Outing(
    id = id,
    outType = outType,
    reason = reason,
    status = status.toModel(),
    student = student.toModel(),
    startAt = startAt,
    endAt = endAt,
    createdAt = createdAt,
    modifiedAt = modifiedAt,
)
