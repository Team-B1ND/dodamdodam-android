package com.b1nd.dodam.club.model

import com.b1nd.dodam.data.core.model.Teacher
import com.b1nd.dodam.data.core.model.toModel

data class Club(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val description: String,
    val subject: String,
    val type: ClubType,
    val image: String?,
    val teacher: Teacher?,
    val state: ClubState
)

internal fun ClubResponse.toModel(): Club = Club(
    id = id,
    name = name,
    shortDescription = shortDescription,
    description = description,
    subject = subject,
    image = image,
    teacher = teacher?.toModel(),
    type = type.toClubType(),
    state = state.toClubState()
)