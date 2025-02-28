package com.b1nd.dodam.club.model

data class ClubInfo(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val description: String,
    val subject: String,
    val type: ClubType,
    val teacher: Int,
    val state: ClubState
)

internal fun ClubResponse.toModel(): ClubInfo = ClubInfo(
    id = id,
    name = name,
    shortDescription = shortDescription,
    description = description,
    subject = subject,
    teacher = teacher,
    type = type.toClubType(),
    state = state.toClubState()
)