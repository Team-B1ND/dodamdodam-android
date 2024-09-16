package com.b1nd.dodam.network.point.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class PointRequest(
    val issueAt: LocalDate,
    val reasonId: Int,
    val studentIds: List<Int>
)