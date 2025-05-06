package com.b1nd.dodam.network.nightstudy.model

import kotlinx.serialization.Serializable

@Serializable
data class NightStudyStudentResponse(
    val id: Long,
    val name: String,
    val grade: Int,
    val room: Int,
    val number: Int,
    val phone: String,
    val profileImage: String,
    val isBanned: Boolean
)