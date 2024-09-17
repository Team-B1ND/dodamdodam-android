package com.b1nd.dodam.member.model

import kotlinx.serialization.Serializable

@Serializable
data class TeacherResponse(
    val name: String,
    val position: String,
    val tel: String,
)
