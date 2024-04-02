package com.b1nd.dodam.data.point.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.point.PointRepository
import com.b1nd.dodam.data.point.model.Point
import com.b1nd.dodam.data.point.model.PointType
import com.b1nd.dodam.data.point.model.toModel
import com.b1nd.dodam.network.point.datasource.PointDataSource
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class PointRepositoryImpl @Inject constructor(
    private val network: PointDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : PointRepository {
    override fun getMyOut(): Flow<Result<ImmutableList<Point>>> {
        return combine(
            flow { emit(network.getMyPoint(PointType.SCHOOL.name)) },
            flow { emit(network.getMyPoint(PointType.DORMITORY.name)) },
        ) { school, dormitory ->
            school.map { it.toModel(PointType.SCHOOL) }.toPersistentList()
                .addAll(dormitory.map { it.toModel(PointType.DORMITORY) })
        }.asResult().flowOn(dispatcher)
    }
}
