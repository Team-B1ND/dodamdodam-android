package com.b1nd.dodam.nightstudy.state

import com.b1nd.dodam.data.nightstudy.model.NightStudy

data class NightStudyScreenUiState(
    val nightStudyUiState: NightStudyUiState = NightStudyUiState.Loading,
)


sealed interface NightStudyUiState {
    data class Success(val data: NightStudy?) : NightStudyUiState
    data object Loading : NightStudyUiState
    data object Error : NightStudyUiState
}
