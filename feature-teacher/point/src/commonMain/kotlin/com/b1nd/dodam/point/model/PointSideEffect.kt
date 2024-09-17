package com.b1nd.dodam.point.model

sealed interface PointSideEffect {
    data class FailedGivePoint(val throwable: Throwable) : PointSideEffect
    data object SuccessGivePoint : PointSideEffect
}
