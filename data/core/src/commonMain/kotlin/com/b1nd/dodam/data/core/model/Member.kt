package com.b1nd.dodam.data.core.model

import com.b1nd.dodam.network.core.model.MemberResponse
import kotlinx.datetime.LocalDateTime

data class Member(
    val id: String,
    val name: String,
    val email: String,
    val role: MemberRole,
    val status: String,
    val profileImage: String?,
    val phone: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
)

fun MemberResponse.toModel() = Member(
    id = id,
    name = name,
    email = email,
    role = role.toModel(),
    status = status,
    profileImage = profileImage,
    phone = phone,
    createdAt = createdAt,
    modifiedAt = modifiedAt,
)
