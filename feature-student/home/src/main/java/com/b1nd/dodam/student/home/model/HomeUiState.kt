package com.b1nd.dodam.student.home.model

import com.b1nd.dodam.data.banner.model.Banner
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import com.b1nd.dodam.data.outing.model.Outing
import com.b1nd.dodam.data.schedule.model.Schedule
import com.b1nd.dodam.wakeupsong.model.WakeupSong
import kotlinx.collections.immutable.ImmutableList

data class HomeUiState(
    val bannerUiState: BannerUiState = BannerUiState.None,
    val mealUiState: MealUiState = MealUiState.Shimmer,
    val wakeupSongUiState: WakeupSongUiState = WakeupSongUiState.Shimmer,
    val outUiState: OutUiState = OutUiState.Shimmer,
    val nightStudyUiState: NightStudyUiState = NightStudyUiState.Shimmer,
    val scheduleUiState: ScheduleUiState = ScheduleUiState.Shimmer,
)

sealed interface BannerUiState {
    data object None : BannerUiState
    data class Success(val data: ImmutableList<Banner>) : BannerUiState
}

sealed interface MealUiState {
    data class Success(val data: ImmutableList<String?>) : MealUiState
    data object Shimmer : MealUiState
    data object Loading : MealUiState
    data class Error(val message: String) : MealUiState
}

sealed interface WakeupSongUiState {
    data class Success(val data: ImmutableList<WakeupSong>) : WakeupSongUiState
    data object Shimmer : WakeupSongUiState
    data object Loading : WakeupSongUiState
    data class Error(val message: String) : WakeupSongUiState
}

sealed interface OutUiState {
    data class Success(val data: Outing?) : OutUiState
    data object Shimmer : OutUiState
    data object Loading : OutUiState
    data class Error(val message: String) : OutUiState
}

sealed interface NightStudyUiState {
    data class Success(val data: NightStudy?) : NightStudyUiState
    data object Shimmer : NightStudyUiState
    data object Loading : NightStudyUiState
    data class Error(val message: String) : NightStudyUiState
}

sealed interface ScheduleUiState {
    data class Success(val data: ImmutableList<Schedule>) : ScheduleUiState
    data object Shimmer : ScheduleUiState
    data object Loading : ScheduleUiState
    data class Error(val message: String) : ScheduleUiState
}
