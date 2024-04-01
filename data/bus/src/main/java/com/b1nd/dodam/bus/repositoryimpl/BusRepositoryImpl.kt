package com.b1nd.dodam.bus.repositoryimpl

import com.b1nd.dodam.bus.datasource.BusDataSource
import com.b1nd.dodam.bus.model.Bus
import com.b1nd.dodam.bus.model.BusResponse
import com.b1nd.dodam.bus.model.toModel
import com.b1nd.dodam.bus.repository.BusRepository
import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BusRepositoryImpl @Inject constructor(
    private val busDataSource: BusDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : BusRepository {
    override fun getBusList(): Flow<Result<ImmutableList<Bus>>> {
        return flow {
            emit(
                busDataSource.getBusList().map { it.toModel() }.toImmutableList()
            )
        }.asResult().flowOn(dispatcher)
    }

    override fun applyBus(): Flow<Result<Unit>> {
        return flow {
            emit(
                busDataSource.applyBus()
            )
        }.asResult().flowOn(dispatcher)
    }
}
