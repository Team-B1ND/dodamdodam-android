package com.b1nd.dodam.data.core.model

import com.b1nd.dodam.network.core.model.MemberRoleResponse

enum class MemberRole {
    STUDENT, PARENT, TEACHER, ADMIN
}

fun MemberRoleResponse.toModel() =
    when (this) {
        MemberRoleResponse.STUDENT -> MemberRole.STUDENT
        MemberRoleResponse.PARENT -> MemberRole.PARENT
        MemberRoleResponse.TEACHER -> MemberRole.TEACHER
        MemberRoleResponse.ADMIN -> MemberRole.ADMIN
    }

/**
 * Return Korean Name
 */
fun MemberRole.getName() =
    when (this) {
        MemberRole.STUDENT -> "학생"
        MemberRole.PARENT -> "학부모"
        MemberRole.TEACHER -> "선생님"
        MemberRole.ADMIN -> "관리자"
    }