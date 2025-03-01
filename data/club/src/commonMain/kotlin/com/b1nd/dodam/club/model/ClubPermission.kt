package com.b1nd.dodam.club.model

enum class ClubPermission {
    CLUB_LEADER,
    CLUB_MEMBER,
}

internal fun String.toClubPermission() = when (this) {
    "CLUB_LEADER" -> ClubPermission.CLUB_LEADER
    "CLUB_MEMBER" -> ClubPermission.CLUB_MEMBER
    else -> ClubPermission.CLUB_MEMBER
}
