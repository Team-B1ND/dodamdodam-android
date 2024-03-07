package com.b1nd.dodam.network.register.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("accessToken") val accessToken: String,
)
