package com.b1nd.dodam.network.nightstudy.model

import com.b1nd.dodam.network.core.model.NetworkStatus
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class ProjectResponse(
    val id: Long,
    val type: String,
    val status: NetworkStatus,
    val name: String,
    val description: String,
    val startAt: LocalDate,
    val endAt: LocalDate,
)
