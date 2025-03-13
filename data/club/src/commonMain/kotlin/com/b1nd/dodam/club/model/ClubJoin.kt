package com.b1nd.dodam.club.model

data class ClubJoin(
    val id: Int,
    val clubPermission: ClubPermission,
    val status: ClubState,
    val priority: String? = null,
    val introduction: String? = null,
    val club: Club,
)

internal fun ClubJoinResponse.toModel(): ClubJoin = ClubJoin(
    id = id,
    clubPermission = clubPermission.toClubPermission(),
    status = status.toClubState(),
    priority = priority,
    introduction = introduction,
    club = club.toModel(),
)
