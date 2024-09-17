package com.b1nd.dodam.common.utiles

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.joinAll

fun <T1, T2, R> combineWhenAllComplete(flow1: Flow<T1>, flow2: Flow<T2>, transform: suspend (T1, T2) -> R): Flow<R> = flow {
    var lastValue1: T1? = null
    var lastValue2: T2? = null

    // 첫 번째 Flow 처리
    val job1 = flow1.onEach { value ->
        lastValue1 = value
    }.launchIn(CoroutineScope(Dispatchers.IO))

    // 두 번째 Flow 처리
    val job2 = flow2.onEach { value ->
        lastValue2 = value
    }.launchIn(CoroutineScope(Dispatchers.IO))

    joinAll(job1, job2)

    // 두 Flow가 모두 종료되었을 때 마지막 값으로 변환 작업 수행
    emit(transform(lastValue1!!, lastValue2!!))
}


fun calculateDaysBetween(startDate: String, endDate: String): Int {
    val monthDays = mapOf(
        1 to 31, 2 to 28, 3 to 31, 4 to 30, 5 to 31, 6 to 30,
        7 to 31, 8 to 31, 9 to 30, 10 to 31, 11 to 30, 12 to 31,
    )

    fun isLeapYear(year: Int) = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))

    fun daysInMonth(year: Int, month: Int): Int {
        return if (month == 2 && isLeapYear(year)) 29 else monthDays[month] ?: 30
    }

    val startParts = startDate.split("-").map { it.toInt() }
    val endParts = endDate.split("-").map { it.toInt() }

    val startYear = startParts[0]
    val startMonth = startParts[1]
    val startDay = startParts[2]

    val endYear = endParts[0]
    val endMonth = endParts[1]
    val endDay = endParts[2]

    // 날짜 차이 계산
    var totalDays = 0

    if (startYear == endYear) {
        if (startMonth == endMonth) {
            totalDays = endDay - startDay
        } else {
            totalDays += daysInMonth(startYear, startMonth) - startDay
            for (month in (startMonth + 1) until endMonth) {
                totalDays += daysInMonth(startYear, month)
            }
            totalDays += endDay
        }
    } else {
        // 시작 연도의 남은 일 수
        totalDays += daysInMonth(startYear, startMonth) - startDay
        for (month in (startMonth + 1)..12) {
            totalDays += daysInMonth(startYear, month)
        }

        // 중간 연도 일 수
        for (year in (startYear + 1) until endYear) {
            totalDays += if (isLeapYear(year)) 366 else 365
        }

        // 종료 연도의 일 수
        for (month in 1 until endMonth) {
            totalDays += daysInMonth(endYear, month)
        }
        totalDays += endDay
    }

    return totalDays
}
