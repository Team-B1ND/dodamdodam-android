package com.b1nd.dodam.network.login.model

import kotlinx.serialization.Serializable

@Serializable
data class StudentResponse(
    val classroom: ClassroomResponse,
    val id: Int,
    val number: Int,
    val phone: String
)