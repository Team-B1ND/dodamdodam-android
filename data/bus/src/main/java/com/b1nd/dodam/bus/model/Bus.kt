package com.b1nd.dodam.bus.model

data class Bus(
    val applyCount: Int,
    val busName: String,
    val description: String,
    val id: Int,
    val leaveTime: String,
    val peopleLimit: Int,
    val timeRequired: String,
)

fun BusResponse.toModel() = Bus(
    applyCount = this.applyCount,
    busName = this.busName,
    description = this.description,
    id = this.id,
    leaveTime = this.leaveTime,
    peopleLimit = this.peopleLimit,
    timeRequired = this.timeRequired,
)
