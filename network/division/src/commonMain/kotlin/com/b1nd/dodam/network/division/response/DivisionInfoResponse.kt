package com.b1nd.dodam.network.division.response

import kotlinx.serialization.Serializable

@Serializable
data class DivisionInfoResponse(
    val id: Int,
    val divisionName: String,
    val description: String,
    val myPermission: String?,
)
