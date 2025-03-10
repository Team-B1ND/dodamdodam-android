package com.b1nd.dodam.common.utiles

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.experimental.ExperimentalTypeInference
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.joinAll
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

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

fun getDate(date: LocalDate): String {
    return "${date.monthNumber}월 ${date.dayOfMonth}일"
}

fun LocalTime.plusHour(hour: Int): LocalTime {
    return LocalTime((this.hour + hour) % 24, minute, second, nanosecond)
}

expect val LocalDate.utcTimeMill: Long

@OptIn(ExperimentalTypeInference::class, ExperimentalContracts::class)
inline fun <T> buildPersistentList(@BuilderInference builderAction: PersistentList.Builder<T>.() -> Unit): PersistentList<T> {
    contract { callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE) }
    return persistentListOf<T>().builder().apply(builderAction).build()
}

/**
 * LocalDateTime을 "5월 4일 수요일" 형식으로 변환하는 함수
 *
 * @param dateTime 변환할 LocalDateTime 객체
 * @return 포맷된 날짜 문자열 (예: "5월 4일 수요일")
 */
fun formatLocalDateTime(dateTime: LocalDateTime): String {
    val date = dateTime.date
    val month = date.monthNumber
    val day = date.dayOfMonth

    val dayOfWeek = when (date.dayOfWeek) {
        DayOfWeek.MONDAY -> "월요일"
        DayOfWeek.TUESDAY -> "화요일"
        DayOfWeek.WEDNESDAY -> "수요일"
        DayOfWeek.THURSDAY -> "목요일"
        DayOfWeek.FRIDAY -> "금요일"
        DayOfWeek.SATURDAY -> "토요일"
        DayOfWeek.SUNDAY -> "일요일"
        else -> ""
    }

    return "${month}월 ${day}일 $dayOfWeek"
}

/**
 * 시간, 날짜 표시시 두자리 숫자로 바꿔주는 함수입니다.
 * @return 문자열을 반환하며 다음과 같이 5가 입력될 경우 `05`로 반환합니다.
 */
fun Int.timeFormat() =
    this.toString().padStart(2, padChar = '0')

operator fun LocalDateTime.minus(other: LocalDateTime): kotlin.time.Duration {
    return this.toInstant(TimeZone.currentSystemDefault()) - other.toInstant(TimeZone.currentSystemDefault())
}