package com.b1nd.dodam.club.model

import com.b1nd.dodam.network.core.model.TeacherResponse
import kotlinx.serialization.Serializable

@Serializable
data class ClubResponse(
    val createdAt: String? = null,
    val modifiedAt: String? = null,
    val id: Int,
    val name: String,
    val shortDescription: String,
    val description: String,
    val subject: String,
    val image: String? = null,
    val type: String,
    val teacher: TeacherResponse?,
    val state: String,
)
