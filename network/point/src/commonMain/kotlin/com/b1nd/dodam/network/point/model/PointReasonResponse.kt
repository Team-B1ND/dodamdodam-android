package com.b1nd.dodam.network.point.model

import kotlinx.serialization.Serializable

@Serializable
data class PointReasonResponse(
    val id: Int,
    val reason: String,
    val score: Int,
    val scoreType: NetworkScoreType,
    val pointType: NetworkPointType,
)
