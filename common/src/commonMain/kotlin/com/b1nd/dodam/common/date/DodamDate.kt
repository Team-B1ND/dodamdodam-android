package com.b1nd.dodam.common.date

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

expect object DodamDate {
    fun now(): LocalDateTime
    fun localDateNow(): LocalDate
}
