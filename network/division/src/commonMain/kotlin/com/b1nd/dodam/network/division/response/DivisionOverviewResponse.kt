package com.b1nd.dodam.network.division.response

import kotlinx.serialization.Serializable

@Serializable
data class DivisionOverviewResponse(
    val id: Int,
    val name: String
)