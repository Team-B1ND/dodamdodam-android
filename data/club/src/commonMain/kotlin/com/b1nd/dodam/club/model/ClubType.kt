package com.b1nd.dodam.club.model

enum class ClubType(val type: String) {
    CREATIVE_ACTIVITY_CLUB("창체"), SELF_DIRECT_ACTIVITY_CLUB("자율")
}

internal fun String.toClubType() = when (this){
    "CREATIVE_ACTIVITY_CLUB" -> ClubType.CREATIVE_ACTIVITY_CLUB
    "SELF_DIRECT_ACTIVITY_CLUB" -> ClubType.SELF_DIRECT_ACTIVITY_CLUB
    else -> ClubType.CREATIVE_ACTIVITY_CLUB
}