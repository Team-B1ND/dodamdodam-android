package com.b1nd.dodam.member.model

data class MemberInfo(
    val createdAt: String,
    val email: String,
    val id: String,
    val modifiedAt: String,
    val name: String,
    val phone: String,
    val profileImage: String?,
    val role: String,
    val status: ActiveStatus,
    val student: Student?,
    val teacher: Teacher?,
)

internal fun MemberInfoResponse.toModel(): MemberInfo = MemberInfo(
    createdAt = createdAt,
    email = email,
    id = id,
    modifiedAt = modifiedAt,
    name = name,
    phone = phone,
    profileImage = profileImage,
    role = role,
    status = status.toActiveStatus(),
    student = student?.toModel(),
    teacher = teacher?.toModel(),
)
