package com.b1nd.dodam.club.model

import kotlinx.serialization.Serializable

@Serializable
data class ClubMemberResponse(
    val id: Int,
    val status: String,
    val permission: String,
    val studentId: Int,
    val name: String,
    val grade: Int,
    val room: Int,
    val number: Int,
    val profileImage: String?,
)
