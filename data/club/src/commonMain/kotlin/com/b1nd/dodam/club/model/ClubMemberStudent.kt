package com.b1nd.dodam.club.model


data class ClubMemberStudent(
    val id: Int,
    val status: ClubState,
    val permissions: ClubPermission,
    val studentId: Int,
    val name: String,
    val grade: Int,
    val room: Int,
    val number: Int,
    val profileImage: String?,
)