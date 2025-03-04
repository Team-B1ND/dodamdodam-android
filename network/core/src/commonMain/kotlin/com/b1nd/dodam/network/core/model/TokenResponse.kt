package com.b1nd.dodam.network.core.model

import kotlinx.serialization.Serializable

@Serializable
internal data class TokenResponse(
    val accessToken: String,
)
