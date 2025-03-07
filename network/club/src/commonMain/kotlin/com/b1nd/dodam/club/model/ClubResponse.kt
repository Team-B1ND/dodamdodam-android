package com.b1nd.dodam.club.model

import com.b1nd.dodam.network.core.model.TeacherResponse
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ClubResponse(
    val createdAt: LocalDateTime? = null,
    val modifiedAt: LocalDateTime? = null,
    val id: Int,
    val clubId: Int?,
    val name: String,
    val shortDescription: String,
    val description: String,
    val subject: String,
    val image: String? = null,
    val type: String,
    val teacher: TeacherResponse?,
    val state: String,
)
