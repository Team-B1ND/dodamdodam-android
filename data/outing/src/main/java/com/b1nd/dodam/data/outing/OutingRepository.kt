package com.b1nd.dodam.data.outing

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.outing.model.Outing
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

interface OutingRepository {
    fun getMyOut(): Flow<Result<ImmutableList<Outing>>>

    fun askOuting(reason: String, startAt: LocalDateTime, endAt: LocalDateTime): Flow<Result<Unit>>

    fun askSleepover(reason: String, startAt: LocalDate, endAt: LocalDate): Flow<Result<Unit>>
}
