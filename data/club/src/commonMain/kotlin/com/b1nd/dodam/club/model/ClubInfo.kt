package com.b1nd.dodam.club.model

import com.b1nd.dodam.data.core.model.Teacher
import com.b1nd.dodam.data.core.model.toModel

data class ClubInfo(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val description: String,
    val subject: String,
    val type: ClubType,
    val teacher: Teacher,
    val state: ClubState
)

internal fun ClubResponse.toModel(): ClubInfo = ClubInfo(
    id = id,
    name = name,
    shortDescription = shortDescription,
    description = description,
    subject = subject,
    teacher = teacher.toModel(),
    type = type.toClubType(),
    state = state.toClubState()
)