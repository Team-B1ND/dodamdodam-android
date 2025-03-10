package com.b1nd.dodam.bus.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class BusResponse(
    val id: Int,
    val busName: String,
    val description: String,
    val peopleLimit: Int,
    val applyCount: Int,
    val leaveTime: LocalDateTime,
    val timeRequired: LocalTime,
)
