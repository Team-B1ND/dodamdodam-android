package com.b1nd.dodam.nightstudy.state

import com.b1nd.dodam.data.nightstudy.model.NightStudy
import kotlinx.collections.immutable.ImmutableList

data class NightStudyScreenUiState(
    val nightStudyUiState: NightStudyUiState = NightStudyUiState.Loading,
    val nightStudyPendingUiState: NightStudyUiState = NightStudyUiState.Loading,
)


sealed interface NightStudyUiState {
    data class Success(val data: ImmutableList<NightStudy?>) : NightStudyUiState
    data object Loading : NightStudyUiState
    data object Error : NightStudyUiState
}
