package com.b1nd.dodam.network.outing.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class SleepoverRequest(
    val reason: String,
    val startAt: LocalDate,
    val endAt: LocalDate,
)
