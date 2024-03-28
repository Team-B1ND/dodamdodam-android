package com.b1nd.dodam.member.model

import kotlinx.serialization.Serializable

@Serializable
data class StudentResponse(
    val grade: Int,
    val id: Int,
    val name: String,
    val number: Int,
    val room: Int,
)