package com.b1nd.dodam.common.date

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

actual object DodamDate {
    actual fun now(): LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime()

}