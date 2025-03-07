package com.b1nd.dodam.club

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlinx.datetime.toJavaLocalDateTime

@Composable
internal fun rememberClubScreenState(lazyListState: LazyListState = rememberLazyListState()): ClubScreenState = remember(lazyListState) {
    ClubScreenState(lazyListState)
}

@Stable
internal class ClubScreenState(
    private val lazyListState: LazyListState,
) {

    val canScrollBackward: Boolean
        get() = lazyListState.canScrollBackward

}
