package com.b1nd.dodam.data.login.model

import com.b1nd.dodam.network.login.model.LoginResponse

data class Token(
    val accessToken: String,
)

internal fun LoginResponse.toModel(): Token = Token(
    accessToken = accessToken
)
