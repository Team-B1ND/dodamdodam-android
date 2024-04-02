package com.b1nd.dodam.student.point.model

import com.b1nd.dodam.data.point.model.Point
import com.b1nd.dodam.data.point.model.PointReason
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PointUiState(
    val isLoading: Boolean = false,
    val schoolPoint: Pair<Int, Int> = 0 to 0,
    val dormitoryPoint: Pair<Int, Int> = 0 to 0,
    val schoolPointReasons: ImmutableList<Point> = persistentListOf(),
    val dormitoryPointReasons: ImmutableList<Point> = persistentListOf()
)
