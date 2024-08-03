package com.b1nd.dodam.network.outing.model

import com.b1nd.dodam.network.core.model.NetworkStatus
import com.b1nd.dodam.network.core.model.StudentResponse
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SleepoverResponse(
    val id: Long,
    val reason: String,
    val status: NetworkStatus,
    val student: StudentResponse,
    val startAt: LocalDate,
    val endAt: LocalDate,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val rejectReason: String?,
)
