package com.b1nd.dodam.network.nightstudy.model

import kotlinx.serialization.Serializable

@Serializable
data class NightStudyStudentResponse(
    val id: Long,
    val name: String,
    val grade: Long,
    val room: Long,
    val number: Long,
    val phone: String,
    val profileImage: String,
    val isBanned: Boolean
)