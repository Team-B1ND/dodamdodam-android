package com.b1nd.dodam.member.model

import kotlinx.serialization.Serializable

@Serializable
data class MemberInfoResponse(
    val createdAt: String,
    val email: String,
    val id: String,
    val modifiedAt: String,
    val name: String,
    val phone: String,
    val profileImage: String?,
    val role: String,
    val status: String,
    val student: StudentResponse?,
    val teacher: TeacherResponse?,
)
