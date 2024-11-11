package com.b1nd.dodam.common.utiles

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant

actual val LocalDate.utcTimeMill: Long
    get() = this
        .atTime(LocalTime(0, 0))
        .toInstant(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
