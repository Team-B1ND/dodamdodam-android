package com.b1nd.dodam.home.model

import com.b1nd.dodam.data.banner.model.Banner
import com.b1nd.dodam.data.meal.model.Meal
import com.b1nd.dodam.data.schedule.model.Schedule
import kotlinx.collections.immutable.ImmutableList

data class HomeUiState(
    val showShimmer: Boolean = true,
    val bannerUiState: BannerUiState = BannerUiState.None,
    val mealUiState: MealUiState = MealUiState.Loading,
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

sealed interface OutUiState {
    data class Success(
        val outAllowCount: Int,
        val outPendingCount: Int,
        val sleepoverAllowCount: Int,
        val sleepoverPendingCount: Int,
    ) : OutUiState
    data object Loading : OutUiState
    data object Error : OutUiState
}

sealed interface NightStudyUiState {
    data class Success(
        val active: Int,
        val pending: Int,
    ) : NightStudyUiState
    data object Loading : NightStudyUiState
    data object Error : NightStudyUiState
}

sealed interface ScheduleUiState {
    data class Success(val data: ImmutableList<Schedule>) : ScheduleUiState
    data object Loading : ScheduleUiState
    data object Error : ScheduleUiState
}
