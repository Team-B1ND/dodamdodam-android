package com.b1nd.dodam.network.login.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val member: MemberResponse,
    val refreshToken: String,
    val token: String
)