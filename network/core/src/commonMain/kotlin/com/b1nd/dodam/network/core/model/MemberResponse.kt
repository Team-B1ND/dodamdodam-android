package com.b1nd.dodam.network.core.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MemberResponse(
    val id: String,
    val name: String,
    val email: String,
    val role: MemberRoleResponse,
    val status: String,
    val profileImage: String?,
    val phone: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
)
