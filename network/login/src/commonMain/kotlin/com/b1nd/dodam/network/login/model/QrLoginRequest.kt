package com.b1nd.dodam.network.login.model

import kotlinx.serialization.Serializable

@Serializable
data class QrLoginRequest(
    val code: String,
    val access: String,
    val refresh: String,
    val clientId: String,
    val word: String,
)
