package com.b1nd.dodam.data.division.repository

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.division.DivisionRepository
import com.b1nd.dodam.data.division.model.DivisionOverview
import com.b1nd.dodam.data.division.model.toModel
import com.b1nd.dodam.network.division.datasource.DivisionDataSource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class DivisionRepositoryImpl(
    private val divisionDataSource: DivisionDataSource,
    private val dispatcher: CoroutineDispatcher,
): DivisionRepository {
    override suspend fun getAllDivisions(
        lastId: Int,
        limit: Int
    ): Flow<Result<ImmutableList<DivisionOverview>>> = flow {
        emit(
            divisionDataSource.getAllDivisions(
                lastId = lastId,
                limit = limit
            )
                .map { it.toModel() }
                .toImmutableList()
        )
    }
        .flowOn(dispatcher)
        .asResult()


    override suspend fun getMyDivisions(
        lastId: Int,
        limit: Int
    ): Flow<Result<ImmutableList<DivisionOverview>>> = flow {
        emit(
            divisionDataSource.getMyDivisions(
                lastId = lastId,
                limit = limit
            )
                .map { it.toModel() }
                .toImmutableList()
        )
    }
        .flowOn(dispatcher)
        .asResult()

}