package com.b1nd.dodam.network.core.model

import kotlinx.serialization.Serializable

@Serializable
enum class MemberRoleResponse {
    STUDENT,
    PARENT,
    TEACHER,
    ADMIN,
}
