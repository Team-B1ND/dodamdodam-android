package com.b1nd.dodam.network.division.request

import kotlinx.serialization.Serializable

@Serializable
data class DivisionCreateRequest(
    val name: String,
    val description: String,
)
