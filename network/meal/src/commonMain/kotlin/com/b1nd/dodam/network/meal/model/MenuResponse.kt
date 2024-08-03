package com.b1nd.dodam.network.meal.model

import kotlinx.serialization.Serializable

@Serializable
data class MenuResponse(
    val name: String,
    val allergies: List<Int>,
)
