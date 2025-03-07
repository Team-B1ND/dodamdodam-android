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

internal fun ClubMemberStudentResponse.toModel(): ClubMemberStudent = ClubMemberStudent(
    id = id,
    status = status.toClubState(),
    permissions = permission.toClubPermission(),
    studentId = studentId,
    name = name,
    grade = grade,
    room = room,
    number = number,
    profileImage = profileImage,
)
