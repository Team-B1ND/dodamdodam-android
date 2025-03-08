package com.b1nd.dodam.club.model

import kotlinx.serialization.Serializable

@Serializable
data class ClubMyJoinedResponse(
    val id: Int,
    val name: String,
    val type: String,
    val myStatus: String,
)
