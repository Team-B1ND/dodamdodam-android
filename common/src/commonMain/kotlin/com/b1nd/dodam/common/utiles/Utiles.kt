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