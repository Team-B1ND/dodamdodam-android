package com.b1nd.dodam.network.nightstudy.model

import kotlinx.serialization.Serializable

@Serializable
data class BanRequest(
    val student: Long,
    val reason: String,
    val ended: String,
)
