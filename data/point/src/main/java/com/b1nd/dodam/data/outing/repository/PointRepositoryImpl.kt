package com.b1nd.dodam.data.outing.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.outing.PointRepository
import com.b1nd.dodam.data.outing.model.Point
import com.b1nd.dodam.data.outing.model.PointType
import com.b1nd.dodam.data.outing.model.toModel
import com.b1nd.dodam.network.point.datasource.PointDataSource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class PointRepositoryImpl @Inject constructor(
    private val network: PointDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : PointRepository {
    override fun getMyOut(type: PointType): Flow<Result<ImmutableList<Point>>> {
        return flow {
            emit(
                network.getMyPoint(type.name).map { it.toModel() }.toImmutableList()
            )
        }.asResult().flowOn(dispatcher)
    }
}
