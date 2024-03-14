package com.b1nd.dodam.data.nightstudy.model

import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.core.model.Student
import com.b1nd.dodam.data.core.model.toModel
import com.b1nd.dodam.network.nightstudy.model.NightStudyResponse
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime

data class NightStudy(
    val id: Long,
    val content: String,
    val status: Status,
    val doNeedPhone: Boolean,
    val reasonForPhone: String?,
    val student: Student,
    val place: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
)

internal fun NightStudyResponse.toModel(): NightStudy = NightStudy(
    id = id,
    content = content,
    status = status.toModel(),
    doNeedPhone = doNeedPhone,
    reasonForPhone = reasonForPhone,
    student = student.toModel(),
    place = place,
    startAt = startAt.atTime(hour = 23, minute = 0, second = 0),
    endAt = endAt.atTime(hour = 23, minute = 0, second = 0),
    createdAt = createdAt,
    modifiedAt = modifiedAt,
)
