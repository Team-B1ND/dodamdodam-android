package com.b1nd.dodam.network.core.model

import kotlinx.serialization.Serializable

@Serializable
data class StudentResponse(
    val id: Long,
    val name: String,
    val grade: Int,
    val room: Int,
    val number: Int,
)
