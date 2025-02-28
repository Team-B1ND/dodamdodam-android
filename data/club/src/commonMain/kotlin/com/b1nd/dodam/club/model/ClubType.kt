package com.b1nd.dodam.club.model

enum class ClubType {
    CREATIVE_ACTIVITY_CLUB, SELF_DIRECT_ACTIVITY_CLUB
}

internal fun String.toClubType() = when (this){
    "CREATIVE_ACTIVITY_CLUB" -> ClubType.CREATIVE_ACTIVITY_CLUB
    "SELF_DIRECT_ACTIVITY_CLUB" -> ClubType.SELF_DIRECT_ACTIVITY_CLUB
    else -> ClubType.CREATIVE_ACTIVITY_CLUB
}