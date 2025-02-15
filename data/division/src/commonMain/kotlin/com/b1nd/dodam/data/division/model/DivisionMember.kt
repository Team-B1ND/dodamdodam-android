package com.b1nd.dodam.data.division.model

import com.b1nd.dodam.data.core.model.MemberRole
import com.b1nd.dodam.data.core.model.toModel
import com.b1nd.dodam.network.division.response.DivisionMemberResponse

data class DivisionMember(
    val id: Int,
    val memberId: String,
    val memberName: String,
    val profileImage: String?,
    val permission: DivisionPermission,
    val grade: Int?,
    val room: Int?,
    val number: Int?,
    val role: MemberRole,
)

internal fun DivisionMemberResponse.toModel() = DivisionMember(
    id = id,
    memberId = memberId,
    memberName = memberName,
    profileImage = profileImage,
    permission = when (permission) {
        "ADMIN" -> DivisionPermission.ADMIN
        "WRITER" -> DivisionPermission.WRITER
        else -> DivisionPermission.READER
    },
    grade = grade,
    room = room,
    number = number,
    role = role.toModel(),
)
