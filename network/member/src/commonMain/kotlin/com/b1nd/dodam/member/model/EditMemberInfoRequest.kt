package com.b1nd.dodam.member.model

import kotlinx.serialization.Serializable

@Serializable
data class EditMemberInfoRequest(
    val email: String,
    val name: String,
    val phone: String,
    val profileImage: String?,
)
