package com.b1nd.dodam.network.login.model

import kotlinx.serialization.Serializable

@Serializable
data class ClassroomResponse(
    val grade: Int,
    val id: Int,
    val place: PlaceResponse,
    val room: Int
)