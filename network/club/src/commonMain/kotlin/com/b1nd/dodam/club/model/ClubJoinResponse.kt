package com.b1nd.dodam.club.model

import kotlinx.serialization.Serializable

@Serializable
data class ClubJoinResponse(
    val id: Int,
    val clubPermission: String,
    val status: String,
    val priority: String? = null,
    val introduction: String? = null,
    val club: ClubResponse,
)
