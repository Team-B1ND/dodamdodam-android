package com.b1nd.dodam.network.login.mdoel

data class LoginResponse(
    val member: Member,
    val refreshToken: String,
    val token: String
)