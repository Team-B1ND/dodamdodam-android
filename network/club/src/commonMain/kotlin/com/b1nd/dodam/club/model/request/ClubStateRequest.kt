package com.b1nd.dodam.club.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ClubStateRequest(
    val clubIds: List<Int>,
    val status: String,
    val reason: String?,
)
