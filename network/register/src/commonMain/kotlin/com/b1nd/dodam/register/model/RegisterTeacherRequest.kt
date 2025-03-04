package com.b1nd.dodam.register.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterTeacherRequest(
    @SerialName("id") val id: String,
    @SerialName("email") val email: String,
    @SerialName("name") val name: String,
    @SerialName("phone") val phone: String,
    @SerialName("pw") val pw: String,
    @SerialName("position") val position: String,
    @SerialName("tel") val tel: String,
)
