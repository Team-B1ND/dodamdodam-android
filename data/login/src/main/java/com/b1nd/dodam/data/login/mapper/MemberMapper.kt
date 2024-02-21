package com.b1nd.dodam.data.login.mapper

import com.b1nd.dodam.model.Member
import com.b1nd.dodam.network.login.model.LoginResponse

internal fun LoginResponse.toModel() = Member(
    email = this.member.email,
    id = this.member.id,
    joinDate = this.member.joinDate,
    name = this.member.name,
    profileImage = this.member.profileImage,
    role = this.member.role,
    status = this.member.status,
)
