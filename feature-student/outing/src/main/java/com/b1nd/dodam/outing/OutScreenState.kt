package com.b1nd.dodam.outing

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlinx.datetime.toJavaLocalDateTime

@Composable
internal fun rememberOutScreenState(lazyListState: LazyListState = rememberLazyListState()): OutScreenState = remember(lazyListState) {
    OutScreenState(lazyListState)
}

@Stable
internal class OutScreenState(
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
