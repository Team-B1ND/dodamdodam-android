package com.b1nd.dodam.data.outing.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.outing.OutingRepository
import com.b1nd.dodam.data.outing.mapper.toModel
import com.b1nd.dodam.model.OutType
import com.b1nd.dodam.model.Outing
import com.b1nd.dodam.network.outing.datasource.OutingDataSource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class OutingRepositoryImpl @Inject constructor(
    private val network: OutingDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : OutingRepository {

    override fun getMyOut(): Flow<Result<ImmutableList<Outing>>> {
        return combine(
            flow { emit(network.getMyOutGoing()) },
            flow { emit(network.getMyOutSleeping()) }
        ) { outGoingResponse, outSleepingResponse ->
            outSleepingResponse.map { it.toModel(OutType.OUTSLEEPING) }.toPersistentList().addAll(
                outGoingResponse.map { it.toModel(OutType.OUTGOING) }
            ).toImmutableList()
        }.asResult().flowOn(dispatcher)
    }
}
