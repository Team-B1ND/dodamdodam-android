package com.b1nd.dodam.member.model

data class MyInfoResponse(
    val createdAt: String,
    val email: String,
    val id: String,
    val modifiedAt: String,
    val name: String,
    val phone: String,
    val profileImage: String?,
    val role: String,
    val status: String,
    val studentResponse: StudentResponse?,
    val teacherResponse: TeacherResponse?,
)