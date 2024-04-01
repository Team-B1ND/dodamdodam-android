package com.b1nd.dodam.bus.model

data class BusResponse(
    val applyCount: Int,
    val busName: String,
    val description: String,
    val id: Int,
    val leaveTime: String,
    val peopleLimit: Int,
    val timeRequired: String
)