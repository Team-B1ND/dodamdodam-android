package com.b1nd.dodam.register.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    @SerialName("email") val email: String,
    @SerialName("grade") val grade: Int,
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("number") val number: Int,
    @SerialName("phone") val phone: String,
    @SerialName("pw") val pw: String,
    @SerialName("room") val room: Int,
)
