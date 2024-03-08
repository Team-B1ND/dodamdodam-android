package com.b1nd.dodam.student.home.model

import com.b1nd.dodam.data.nightstudy.model.NightStudy
import com.b1nd.dodam.data.outing.model.Outing
import com.b1nd.dodam.wakeupsong.model.WakeupSong
import kotlinx.collections.immutable.ImmutableList

data class HomeUiState(
    val mealUiState: MealUiState = MealUiState.Loading,
    val wakeupSongUiState: WakeupSongUiState = WakeupSongUiState.Loading,
    val outUiState: OutUiState = OutUiState.Loading,
    val nightStudyUiState: NightStudyUiState = NightStudyUiState.Loading
)

sealed interface MealUiState {
    data class Success(val data: ImmutableList<String?>) : MealUiState
    data object Loading : MealUiState
    data class Error(val message: String) : MealUiState
}

sealed interface WakeupSongUiState {
    data class Success(val data: ImmutableList<WakeupSong>) : WakeupSongUiState
    data object Loading : WakeupSongUiState
    data class Error(val message: String) : WakeupSongUiState
}

sealed interface OutUiState {
    data class Success(val data: Outing?) : OutUiState
    data object Loading : OutUiState
    data class Error(val message: String) : OutUiState
}

sealed interface NightStudyUiState {
    data class Success(val data: NightStudy?) : NightStudyUiState
    data object Loading : NightStudyUiState
    data class Error(val message: String) : NightStudyUiState
}
