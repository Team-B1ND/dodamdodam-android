package com.b1nd.dodam.datastore.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val pw: String = "",
    val token: String = "",
    val pushToken: String = "",
)
