package com.b1nd.dodam.data.outing.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.outing.OutingRepository
import com.b1nd.dodam.data.outing.model.Outing
import com.b1nd.dodam.data.outing.model.toModel
import com.b1nd.dodam.network.outing.datasource.OutingDataSource
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

internal class OutingRepositoryImpl @Inject constructor(
    private val network: OutingDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : OutingRepository {

    override fun getMyOut(): Flow<Result<ImmutableList<Outing>>> {
        return combine(
            flow { emit(network.getMyOuting()) },
            flow { emit(network.getMySleepover()) },
        ) { outingResponse, sleepoverResponse ->
            sleepoverResponse.map { it.toModel() }.toPersistentList().addAll(
                outingResponse.map { it.toModel() },
            ).toImmutableList()
        }.asResult().flowOn(dispatcher)
    }

    override fun askOuting(
        reason: String,
        startAt: LocalDateTime,
        endAt: LocalDateTime
    ): Flow<Result<Unit>> {
        return flow {
            emit(network.askOuting(reason, startAt, endAt))
        }.asResult().flowOn(dispatcher)
    }

    override fun askSleepover(
        reason: String,
        startAt: LocalDate,
        endAt: LocalDate
    ): Flow<Result<Unit>> {
        return flow {
            emit(network.askSleepover(reason, startAt, endAt))
        }.asResult().flowOn(dispatcher)
    }
}
