package com.b1nd.dodam.network.division.response

import com.b1nd.dodam.network.core.model.MemberRoleResponse
import kotlinx.serialization.Serializable

@Serializable
data class DivisionMemberResponse(
    val id: Int,
    val memberName: String,
    val profileImage: String?,
    val permission: String,
    val grade: Int,
    val room: Int,
    val number: Int,
    val role: MemberRoleResponse,
)
