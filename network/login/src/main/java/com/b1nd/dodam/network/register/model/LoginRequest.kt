package com.b1nd.dodam.network.register.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val id: String,
    val pw: String,
)
