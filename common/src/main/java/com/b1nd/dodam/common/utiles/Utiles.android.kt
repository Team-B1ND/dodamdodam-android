package com.b1nd.dodam.common.utiles

import java.time.LocalTime
import java.time.ZoneId
import kotlinx.datetime.LocalDate
import kotlinx.datetime.atTime
import kotlinx.datetime.toJavaLocalDate

actual val LocalDate.utcTimeMill: Long
    get() = this
        .toJavaLocalDate()
        .atTime(LocalTime.MIDNIGHT)
        .atZone(ZoneId.of("UTC"))
        .toInstant()
        .toEpochMilli()
