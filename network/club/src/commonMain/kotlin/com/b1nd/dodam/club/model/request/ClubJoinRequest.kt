package com.b1nd.dodam.club.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ClubJoinRequest(
    val clubId: Int,
    val clubPriority: String?,
    val introduction: String,
)
