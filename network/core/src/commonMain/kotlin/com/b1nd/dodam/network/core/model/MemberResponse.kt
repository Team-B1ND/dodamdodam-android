package com.b1nd.dodam.network.core.model

import kotlinx.serialization.Serializable

@Serializable
data class MemberResponse(
    val createdAt: String,
    val modifiedAt: String,
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val status: String,
    val profileImage: String?,
    val phone: String
)
