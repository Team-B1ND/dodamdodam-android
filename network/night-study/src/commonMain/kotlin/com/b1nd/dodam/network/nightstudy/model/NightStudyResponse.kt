package com.b1nd.dodam.network.nightstudy.model

import com.b1nd.dodam.network.core.model.NetworkStatus
import com.b1nd.dodam.network.core.model.StudentImageResponse
import com.b1nd.dodam.network.core.model.StudentResponse
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class NightStudyResponse(
    val id: Long,
    val content: String,
    val status: NetworkStatus,
    val doNeedPhone: Boolean,
    val reasonForPhone: String?,
    val student: StudentResponse,
    val startAt: LocalDate,
    val endAt: LocalDate,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val rejectReason: String?,
)

@Serializable
data class MyBanResponse(
    val id: Long,
    val student: StudentImageResponse,
    val banReason: String?,
    val started: LocalDate?,
    val ended: LocalDate?
)
