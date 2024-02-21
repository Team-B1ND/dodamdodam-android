package com.b1nd.dodam.network.login.model

import kotlinx.serialization.Serializable

@Serializable
data class TypeResponse(
    val id: Int,
    val name: String
)