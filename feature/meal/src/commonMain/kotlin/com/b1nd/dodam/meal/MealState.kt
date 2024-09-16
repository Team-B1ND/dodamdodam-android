package com.b1nd.dodam.meal

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.b1nd.dodam.common.date.DodamDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@Composable
internal fun rememberMealScreenState(lazyListState: LazyListState = rememberLazyListState()): MealScreenState = remember(lazyListState) {
    MealScreenState(lazyListState)
}

@Stable
internal class MealScreenState(
    val lazyListState: LazyListState,
) {
    private val current = DodamDate.now()
    private val currentTime = current.time
    val currentDate = current.date

    val canScrollBackward: Boolean
        get() = lazyListState.canScrollBackward

    val canScrollForward: Boolean
        get() = lazyListState.canScrollForward

    val mealTime: MealTime = when {
        currentTime <= LocalTime(8, 20) -> MealTime.BREAKFAST
        currentTime <= LocalTime(13, 30) -> MealTime.LUNCH
        currentTime <= LocalTime(19, 10) -> MealTime.DINNER
        else -> MealTime.TOMORROW
    }

    fun isDateInToday(date: LocalDate): Boolean = currentDate == date
}

enum class MealTime {
    BREAKFAST,
    LUNCH,
    DINNER,
    TOMORROW,
}