package com.b1nd.dodam.network.login.mdoel

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val member: Member,
    val refreshToken: String,
    val token: String
)