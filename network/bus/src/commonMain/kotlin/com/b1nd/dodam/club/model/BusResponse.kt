package com.b1nd.dodam.club.model

import kotlinx.serialization.Serializable

@Serializable
data class BusResponse(
    val applyCount: Int,
    val busName: String,
    val description: String,
    val id: Int,
    val leaveTime: String,
    val peopleLimit: Int,
    val timeRequired: String,
)
