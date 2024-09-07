package com.b1nd.dodam.data.schedule.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.schedule.ScheduleRepository
import com.b1nd.dodam.data.schedule.model.Schedule
import com.b1nd.dodam.data.schedule.model.toModel
import com.b1nd.dodam.network.schedule.datasource.ScheduleDataSource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.LocalDate

internal class ScheduleRepositoryImpl constructor(
    private val network: ScheduleDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ScheduleRepository {
    override fun getScheduleBetweenPeriods(startAt: LocalDate, endAt: LocalDate): Flow<Result<ImmutableList<Schedule>>> {
        return flow {
            emit(
                network.getScheduleBetweenPeriods(
                    startAt = startAt,
                    endAt = endAt,
                ).map {
                    it.toModel()
                }.toImmutableList(),
            )
        }.asResult().flowOn(dispatcher)
    }
}
