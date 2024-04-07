package com.b1nd.dodam.register.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val message: String,
    val status: Int,
)
