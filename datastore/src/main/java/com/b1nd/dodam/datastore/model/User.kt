package com.b1nd.dodam.datastore.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = null,
    val pw: String? = null,
    val token: String? = null,
)
