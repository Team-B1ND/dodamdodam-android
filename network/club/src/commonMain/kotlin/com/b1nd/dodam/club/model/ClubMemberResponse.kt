package com.b1nd.dodam.club.model

data class ClubMemberResponse(
    val id: Int,
    val status: ClubStateResponse,
    val permissions: ClubPermissionResponse,
    val studentId: Int,
    val name: String,
    val grade: Int,
    val room: Int,
    val number: Int,
    val profileImage: String?
)
