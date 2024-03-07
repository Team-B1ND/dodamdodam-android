package com.b1nd.dodam.network.outing.model

import com.b1nd.dodam.network.core.model.NetworkStatus
import com.b1nd.dodam.network.core.model.StudentResponse
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class OutingResponse(
    val id: Long,
    val reason: String,
    val status: NetworkStatus,
    val student: StudentResponse,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
)
