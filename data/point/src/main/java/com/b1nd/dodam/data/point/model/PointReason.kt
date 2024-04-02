package com.b1nd.dodam.data.point.model

import com.b1nd.dodam.network.point.model.PointReasonResponse

data class PointReason(
    val id: Int,
    val reason: String,
    val score: Int,
    val scoreType: ScoreType,
    val pointType: PointType,
)

internal fun PointReasonResponse.toModel() = PointReason(
    id = id,
    reason = reason,
    score = score,
    scoreType = scoreType.toModel(),
    pointType = pointType.toModel(),
)
