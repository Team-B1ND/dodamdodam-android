package com.b1nd.dodam.club.model

import kotlinx.serialization.Serializable

@Serializable
data class ClubResponse(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val description: String,
    val subject: String,
    val type: String,
    val teacher: Int,
    val state: String
)
