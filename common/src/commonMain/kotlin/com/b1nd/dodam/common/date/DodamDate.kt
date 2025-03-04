package com.b1nd.dodam.common.date

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

expect object DodamDate {
    fun now(): LocalDateTime
    fun localDateNow(): LocalDate
    fun localTimeNow(): LocalTime
}
