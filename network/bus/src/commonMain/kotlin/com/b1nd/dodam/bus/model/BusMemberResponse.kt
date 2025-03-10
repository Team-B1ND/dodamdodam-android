package com.b1nd.dodam.bus.model

import kotlinx.serialization.Serializable

@Serializable
data class BusMemberResponse(
    val memberId: String,
    val name: String,
    val email: String,
    val phone: String,
)