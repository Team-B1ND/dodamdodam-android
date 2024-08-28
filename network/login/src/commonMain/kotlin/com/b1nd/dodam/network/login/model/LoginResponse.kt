package com.b1nd.dodam.network.login.model

import com.b1nd.dodam.network.core.model.MemberResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("accessToken") val accessToken: String,
    @SerialName("member") val member: MemberResponse,
)
