package com.b1nd.dodam.data.nightstudy.model

import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.core.model.toModel
import com.b1nd.dodam.network.nightstudy.model.ProjectResponse
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime

data class Project(
    val id: Long,
    val type: String,
    val status: Status,
    val room: String,
    val name: String,
    val description: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
)

internal fun ProjectResponse.toModel(): Project = Project(
    id = id,
    type = type,
    status = status.toModel(),
    room = room,
    name = name,
    description = description,
    startAt = startAt.atTime(hour = 23, minute = 0, second = 0),
    endAt = endAt.atTime(hour = 23, minute = 0, second = 0),
)
