package com.b1nd.dodam.network.login.model

import kotlinx.serialization.Serializable

@Serializable
data class QrLoginResponse(
    val status: Int,
    val message: String,
)
