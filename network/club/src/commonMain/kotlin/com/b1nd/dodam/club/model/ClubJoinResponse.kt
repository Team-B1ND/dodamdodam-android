package com.b1nd.dodam.club.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

@Serializable
data class ClubJoinResponse(
    val id: Int,
    val clubPermission: ClubPermissionResponse,
    val status: ClubStateResponse,
    val club: ClubResponse
)
