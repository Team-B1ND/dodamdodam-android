package com.b1nd.dodam.bus.repositoryimpl

import com.b1nd.dodam.bus.datasource.BusDataSource
import com.b1nd.dodam.bus.model.Bus
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

class BusRepositoryImpl(
    private val busDataSource: BusDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : BusRepository {
    override fun getBusList(): Flow<Result<ImmutableList<Bus>>> {
        return flow {
            emit(
                busDataSource.getBusList().map { it.toModel() }.toImmutableList(),
            )
        }.asResult().flowOn(dispatcher)
    }

    override fun applyBus(id: Int): Flow<Result<Unit>> {
        return flow {
            emit(
                busDataSource.applyBus(id),
            )
        }.asResult().flowOn(dispatcher)
    }

    override fun deleteBus(id: Int): Flow<Result<Unit>> {
        return flow {
            emit(
                busDataSource.deleteBus(id),
            )
        }.asResult().flowOn(dispatcher)
    }

    override fun updateBus(id: Int): Flow<Result<Unit>> {
        return flow {
            emit(
                busDataSource.updateBus(id),
            )
        }.asResult().flowOn(dispatcher)
    }

    override fun getMyBus(): Flow<Result<Bus>> {
        return flow {
            emit(
                busDataSource.getMyBus().toModel(),
            )
        }.asResult().flowOn(dispatcher)
    }
}
