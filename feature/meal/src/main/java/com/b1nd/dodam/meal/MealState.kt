package com.b1nd.dodam.meal

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import java.time.LocalDateTime
import java.time.LocalTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate

@Composable
internal fun rememberMealScreenState(lazyListState: LazyListState = rememberLazyListState()): MealScreenState = remember(lazyListState) {
    MealScreenState(lazyListState)
}

@Stable
internal class MealScreenState(
    val lazyListState: LazyListState,
) {
    private val current = LocalDateTime.now()
    private val currentTime = current.toLocalTime()
    val currentDate = current.toLocalDate()

    val canScrollBackward: Boolean
        get() = lazyListState.canScrollBackward

    val canScrollForward: Boolean
        get() = lazyListState.canScrollForward

    val mealTime: MealTime = when {
        currentTime <= LocalTime.of(8, 20) -> MealTime.BREAKFAST
        currentTime <= LocalTime.of(13, 30) -> MealTime.LUNCH
        currentTime <= LocalTime.of(19, 10) -> MealTime.DINNER
        else -> MealTime.TOMORROW
    }

    fun isDateInToday(date: LocalDate): Boolean = currentDate.toKotlinLocalDate() == date
}

enum class MealTime {
    BREAKFAST,
    LUNCH,
    DINNER,
    TOMORROW,
}
