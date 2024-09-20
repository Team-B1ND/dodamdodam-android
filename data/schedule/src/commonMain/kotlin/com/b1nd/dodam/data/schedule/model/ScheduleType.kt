package com.b1nd.dodam.data.schedule.model

import com.b1nd.dodam.network.schedule.model.NetworkScheduleType

enum class ScheduleType {
    ACADEMIC,
    HOLIDAY,
}

internal fun NetworkScheduleType.toModel(): ScheduleType = when (this) {
    NetworkScheduleType.ACADEMIC -> ScheduleType.ACADEMIC
    NetworkScheduleType.HOLIDAY -> ScheduleType.HOLIDAY
}
