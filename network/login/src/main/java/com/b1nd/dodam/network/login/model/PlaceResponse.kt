package com.b1nd.dodam.network.login.model

import kotlinx.serialization.Serializable

@Serializable
data class PlaceResponse(
    val id: Int,
    val name: String,
    val type: TypeResponse
)