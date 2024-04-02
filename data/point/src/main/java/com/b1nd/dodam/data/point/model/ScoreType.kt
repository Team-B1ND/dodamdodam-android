package com.b1nd.dodam.data.point.model

import com.b1nd.dodam.network.point.model.NetworkPointType
import com.b1nd.dodam.network.point.model.NetworkScoreType

enum class ScoreType {
    BONUS,
    MINUS,
    OFFSET,
}

internal fun NetworkScoreType.toModel() = when (this) {
    NetworkScoreType.BONUS -> ScoreType.BONUS
    NetworkScoreType.MINUS -> ScoreType.MINUS
    NetworkScoreType.OFFSET -> ScoreType.OFFSET
}