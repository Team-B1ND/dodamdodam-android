package com.b1nd.dodam.data.login.model

import com.b1nd.dodam.network.login.model.LoginResponse

data class Member(
    val accessToken: String,
    val role: String,
)

internal fun LoginResponse.toModel(): Member = Member(
    accessToken = accessToken,
    role = member.role,
)
