package com.b1nd.dodam.common.date

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import platform.Foundation.NSDate
import platform.Foundation.now

actual object DodamDate {
    actual fun now(): LocalDateTime = NSDate
        .now()
        .toKotlinInstant()
        .toLocalDateTime(TimeZone.currentSystemDefault())

    actual fun localDateNow(): LocalDate = now().date
}
