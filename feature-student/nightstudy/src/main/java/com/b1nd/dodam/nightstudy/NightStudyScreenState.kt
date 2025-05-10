package com.b1nd.dodam.nightstudy

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlinx.datetime.toJavaLocalDateTime

@Composable
internal fun rememberNightStudyScreenState(lazyListState: LazyListState = rememberLazyListState()): NightStudyScreenState = remember(lazyListState) {
    NightStudyScreenState(lazyListState)
}

@Stable
internal class NightStudyScreenState(
    val lazyListState: LazyListState,
) {
    private val current = LocalDateTime.now()

    val canScrollBackward: Boolean
        get() = lazyListState.canScrollBackward

    fun getProgress(startAt: kotlinx.datetime.LocalDateTime, endAt: kotlinx.datetime.LocalDateTime): Float {
        return ChronoUnit.SECONDS.between(
            startAt.toJavaLocalDateTime(),
            current,
        ).toFloat() / ChronoUnit.SECONDS.between(
            startAt.toJavaLocalDateTime(),
            endAt.toJavaLocalDateTime(),
        )
    }
}
