package com.b1nd.dodam.club.model

enum class ClubState {
    ALLOWED,PENDING, REJECTED, WAITING,DELETED
}

internal fun String.toClubState() = when (this){
    "ALLOWED" -> ClubState.ALLOWED
    "PENDING" -> ClubState.PENDING
    "REJECTED" -> ClubState.REJECTED
    "WAITING" -> ClubState.WAITING
    "DELETED" -> ClubState.DELETED
    else -> ClubState.WAITING
}