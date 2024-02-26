package com.b1nd.dodam.network.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    @SerialName("data") val data: T? = null,
    @SerialName("message") val message: String,
    @SerialName("status") val status: Int,
)
