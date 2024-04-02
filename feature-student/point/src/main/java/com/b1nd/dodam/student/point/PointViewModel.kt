package com.b1nd.dodam.student.point

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.point.PointRepository
import com.b1nd.dodam.data.point.model.Point
import com.b1nd.dodam.data.point.model.PointReason
import com.b1nd.dodam.data.point.model.PointType
import com.b1nd.dodam.data.point.model.ScoreType
import com.b1nd.dodam.student.point.model.PointUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointViewModel @Inject constructor(
    private val pointRepository: PointRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PointUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            pointRepository.getMyOut().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                schoolPoint = result.data.getScore(
                                    ScoreType.MINUS,
                                    PointType.SCHOOL
                                ) - result.data.getScore(
                                    ScoreType.OFFSET,
                                    PointType.SCHOOL
                                ) to result.data.getScore(
                                    ScoreType.BONUS,
                                    PointType.SCHOOL
                                ),
                                dormitoryPoint = result.data.getScore(
                                    ScoreType.MINUS,
                                    PointType.DORMITORY
                                ) - result.data.getScore(
                                    ScoreType.OFFSET,
                                    PointType.DORMITORY
                                ) to result.data.getScore(
                                    ScoreType.BONUS,
                                    PointType.DORMITORY
                                ),
                                schoolPointReasons = result.data.getPointReason(PointType.SCHOOL),
                                dormitoryPointReasons = result.data.getPointReason(PointType.DORMITORY)
                            )
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Result.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                    }
                }
            }
        }
    }

    private fun List<Point>.getScore(scoreType: ScoreType, pointType: PointType): Int =
        this.filter { it.reason.scoreType == scoreType }
            .filter { it.pointType == pointType }
            .sumOf { it.reason.score }

    private fun List<Point>.getPointReason(pointType: PointType): ImmutableList<Point> =
        this.filter {
            it.pointType == pointType
        }.toImmutableList()
}
