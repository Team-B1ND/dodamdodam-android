package com.b1nd.dodam.data.outing

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.outing.model.Outing
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

interface OutingRepository {
    fun getMyOut(): Flow<Result<ImmutableList<Outing>>>

    fun getOutings(date: LocalDate): Flow<Result<ImmutableList<Outing>>>

    fun askOuting(reason: String, startAt: LocalDateTime, endAt: LocalDateTime, isDinner: Boolean?): Flow<Result<Unit>>

    fun getSleepovers(date: LocalDate): Flow<Result<ImmutableList<Outing>>>

    fun getAllSleepovers(date: LocalDate): Flow<Result<ImmutableList<Outing>>>

    fun askSleepover(reason: String, startAt: LocalDate, endAt: LocalDate): Flow<Result<Unit>>

    fun deleteOuting(id: Long): Flow<Result<Unit>>

    fun deleteSleepover(id: Long): Flow<Result<Unit>>

    fun allowSleepover(id: Long): Flow<Result<Unit>>

    fun allowGoing(id: Long): Flow<Result<Unit>>

    fun rejectSleepover(id: Long): Flow<Result<Unit>>

    fun rejectGoing(id: Long): Flow<Result<Unit>>
}
