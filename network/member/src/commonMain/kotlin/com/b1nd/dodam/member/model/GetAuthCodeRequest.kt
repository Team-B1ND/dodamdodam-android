package com.b1nd.dodam.member.model

import kotlinx.serialization.Serializable

@Serializable
data class GetAuthCodeRequest(
    val identifier: String,
)

@Serializable
data class VerifyAuthCodeRequest(
    val identifier: String,
    val authCode: String,
)
