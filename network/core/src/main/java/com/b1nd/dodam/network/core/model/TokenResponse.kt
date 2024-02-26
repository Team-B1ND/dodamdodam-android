package com.b1nd.dodam.network.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenResponse(
    @SerialName("token") val accessToken: String,
)
