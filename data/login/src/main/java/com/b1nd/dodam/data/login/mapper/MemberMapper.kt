package com.b1nd.dodam.data.login.mapper

import com.b1nd.dodam.model.Token
import com.b1nd.dodam.network.login.model.LoginResponse

internal fun LoginResponse.toModel() = Token(
    accessToken = accessToken,
)
