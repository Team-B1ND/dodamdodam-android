package com.b1nd.dodam.member.model

import kotlinx.serialization.Serializable


@Serializable
data class AuthCodeRequest(
    val identifier: String
)
