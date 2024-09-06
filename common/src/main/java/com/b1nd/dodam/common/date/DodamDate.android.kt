package com.b1nd.dodam.common.date

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime

actual object DodamDate {
    actual fun now(): LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime()
    actual fun localDateNow(): LocalDate = java.time.LocalDate.now().toKotlinLocalDate()
}
