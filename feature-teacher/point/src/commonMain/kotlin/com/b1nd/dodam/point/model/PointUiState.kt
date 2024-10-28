package com.b1nd.dodam.point.model

import com.b1nd.dodam.data.point.model.PointReason
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

enum class PointPage {
    GIVE,
    SELECT
}

data class PointUiState(
    val isNetworkLoading: Boolean = false,
    val uiState: PointLoadingUiState = PointLoadingUiState.Loading,
)

sealed interface PointLoadingUiState {
    data class Success(
        val students:  ImmutableList<PointStudentModel> = persistentListOf(),
        val reasons: ImmutableList<PointReason> = persistentListOf(),
    ) : PointLoadingUiState
    data object Loading : PointLoadingUiState
    data object Error : PointLoadingUiState
}
