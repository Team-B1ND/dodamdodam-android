package com.b1nd.dodam.data.outing.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.outing.OutingRepository
import com.b1nd.dodam.data.outing.model.Outing
import com.b1nd.dodam.data.outing.model.toModel
import com.b1nd.dodam.network.outing.datasource.OutingDataSource
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

internal class OutingRepositoryImpl(
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

    override fun getOutings(date: LocalDate): Flow<Result<ImmutableList<Outing>>> {
        return flow {
            emit(network.getOutings(date).map { it.toModel() }.toImmutableList())
        }.asResult().flowOn(dispatcher)
    }

    override fun askOuting(reason: String, startAt: LocalDateTime, endAt: LocalDateTime, isDinner: Boolean?): Flow<Result<Unit>> {
        return flow {
            emit(network.askOuting(reason, startAt, endAt, isDinner))
        }.asResult().flowOn(dispatcher)
    }

    override fun getSleepovers(date: LocalDate): Flow<Result<ImmutableList<Outing>>> {
        return flow {
            emit(network.getSleepovers(date).map { it.toModel() }.toImmutableList())
        }.asResult().flowOn(dispatcher)
    }

    override fun getAllSleepovers(date: LocalDate): Flow<Result<ImmutableList<Outing>>> {
        return flow {
            emit(network.getAllSleepovers(date).map { it.toModel() }.toImmutableList())
        }.asResult().flowOn(dispatcher)
    }

    override fun askSleepover(reason: String, startAt: LocalDate, endAt: LocalDate): Flow<Result<Unit>> {
        return flow {
            emit(network.askSleepover(reason, startAt, endAt))
        }.asResult().flowOn(dispatcher)
    }

    override fun deleteOuting(id: Long): Flow<Result<Unit>> {
        return flow {
            emit(network.deleteOuting(id))
        }.asResult().flowOn(dispatcher)
    }

    override fun deleteSleepover(id: Long): Flow<Result<Unit>> {
        return flow {
            emit(network.deleteSleepover(id))
        }.asResult().flowOn(dispatcher)
    }

    override fun allowSleepover(id: Long): Flow<Result<Unit>> {
        return flow {
            emit(network.allowSleepover(id))
        }.asResult().flowOn(dispatcher)
    }
    override fun allowGoing(id: Long): Flow<Result<Unit>> {
        return flow {
            emit(network.allowGoing(id))
        }.asResult().flowOn(dispatcher)
    }

    override fun rejectSleepover(id: Long): Flow<Result<Unit>> {
        return flow {
            emit(network.rejectSleepover(id))
        }.asResult().flowOn(dispatcher)
    }
    override fun rejectGoing(id: Long): Flow<Result<Unit>> {
        return flow {
            emit(network.rejectGoing(id))
        }.asResult().flowOn(dispatcher)
    }
}
