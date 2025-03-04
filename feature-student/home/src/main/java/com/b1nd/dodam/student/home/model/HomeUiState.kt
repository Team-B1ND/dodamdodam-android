package com.b1nd.dodam.student.home.model

import com.b1nd.dodam.data.banner.model.Banner
import com.b1nd.dodam.data.meal.model.Meal
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import com.b1nd.dodam.data.outing.model.Outing
import com.b1nd.dodam.data.schedule.model.Schedule
import com.b1nd.dodam.wakeupsong.model.WakeupSong
import kotlinx.collections.immutable.ImmutableList

data class HomeUiState(
    val showShimmer: Boolean = true,
    val bannerUiState: BannerUiState = BannerUiState.None,
    val mealUiState: MealUiState = MealUiState.Loading,
    val wakeupSongUiState: WakeupSongUiState = WakeupSongUiState.Loading,
    val outUiState: OutUiState = OutUiState.Loading,
    val nightStudyUiState: NightStudyUiState = NightStudyUiState.Loading,
    val scheduleUiState: ScheduleUiState = ScheduleUiState.Loading,
)

sealed interface BannerUiState {
    data object None : BannerUiState
    data class Success(val data: ImmutableList<Banner>) : BannerUiState
}

sealed interface MealUiState {
    data class Success(val data: Meal) : MealUiState
    data object Loading : MealUiState
    data object Error : MealUiState
}

sealed interface WakeupSongUiState {
    data class Success(val data: ImmutableList<WakeupSong>) : WakeupSongUiState
    data object Loading : WakeupSongUiState
    data object Error : WakeupSongUiState
}

sealed interface OutUiState {
    data class Success(val data: Outing?) : OutUiState
    data object Loading : OutUiState
    data object Error : OutUiState
}

sealed interface NightStudyUiState {
    data class Success(val data: NightStudy?) : NightStudyUiState
    data object Loading : NightStudyUiState
    data object Error : NightStudyUiState
}

sealed interface ScheduleUiState {
    data class Success(val data: ImmutableList<Schedule>) : ScheduleUiState
    data object Loading : ScheduleUiState
    data object Error : ScheduleUiState
}
