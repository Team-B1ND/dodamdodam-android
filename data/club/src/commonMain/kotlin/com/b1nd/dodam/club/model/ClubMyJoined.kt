package com.b1nd.dodam.club.model

data class ClubMyJoined(
    val id: Int,
    val name: String,
    val type: ClubType,
    val myStatus: ClubState,
)

internal fun ClubMyJoinedResponse.toModel(): ClubMyJoined = ClubMyJoined(
    id = id,
    name = name,
    type = type.toClubType(),
    myStatus = myStatus.toClubState(),
)
