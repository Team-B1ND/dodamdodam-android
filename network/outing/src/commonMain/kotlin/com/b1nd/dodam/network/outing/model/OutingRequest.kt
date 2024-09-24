package com.b1nd.dodam.network.outing.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class OutingRequest(
    val reason: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val isDinner: Boolean?,
)
