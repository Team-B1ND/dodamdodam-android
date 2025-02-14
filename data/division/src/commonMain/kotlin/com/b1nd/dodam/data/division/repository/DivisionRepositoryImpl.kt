package com.b1nd.dodam.data.division.repository

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.division.DivisionRepository
import com.b1nd.dodam.data.division.model.DivisionInfo
import com.b1nd.dodam.data.division.model.DivisionMember
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
        limit: Int,
        keyword: String,
    ): Flow<Result<ImmutableList<DivisionOverview>>> = flow {
        emit(
            divisionDataSource.getAllDivisions(
                lastId = lastId,
                limit = limit,
                keyword = keyword,
            )
                .map { it.toModel() }
                .toImmutableList()
        )
    }
        .flowOn(dispatcher)
        .asResult()


    override suspend fun getMyDivisions(
        lastId: Int,
        limit: Int,
        keyword: String,
    ): Flow<Result<ImmutableList<DivisionOverview>>> = flow {
        emit(
            divisionDataSource.getMyDivisions(
                lastId = lastId,
                limit = limit,
                keyword = keyword,
            )
                .map { it.toModel() }
                .toImmutableList()
        )
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun getDivision(id: Int): Flow<Result<DivisionInfo>> = flow {
        emit(
            divisionDataSource.getDivision(
                id = id,
            ).toModel()
        )
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun getDivisionMembers(id: Int, status: Status): Flow<Result<ImmutableList<DivisionMember>>> = flow {

        emit(
            divisionDataSource.getDivisionMembers(
                id = id,
                status = status.name,
            )
                .map {
                    it.toModel()
                }
                .toImmutableList()
        )
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun getDivisionMembersCnt(
        id: Int,
        status: Status
    ): Flow<Result<Int>> = flow {

        emit(
            divisionDataSource.getDivisionMembersCnt(
                id = id,
                status = status.name,
            )
        )
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun deleteDivisionMembers(
        divisionId: Int,
        memberId: List<Int>
    ): Flow<Result<Unit>> = flow {
        emit(
            divisionDataSource.deleteDivisionMembers(
                divisionId = divisionId,
                memberId = memberId,
            )
        )
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun postDivisionApplyRequest(divisionId: Int): Flow<Result<Unit>> = flow {
        emit(
            divisionDataSource.postDivisionApplyRequest(
                divisionId = divisionId
            )
        )
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun patchDivisionMembers(
        divisionId: Int,
        memberId: List<Int>,
        status: Status
    ): Flow<Result<Unit>> = flow {
        emit(
            divisionDataSource.patchDivisionMembers(
                divisionId = divisionId,
                memberId = memberId,
                status = status.name
            )
        )
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun postDivisionAddMembers(
        divisionId: Int,
        memberId: List<String>
    ): Flow<Result<Unit>> = flow {
        emit(
            divisionDataSource.postDivisionAddMembers(
                divisionId = divisionId,
                memberId = memberId,
            )
        )
    }
        .flowOn(dispatcher)
        .asResult()

    override suspend fun postCreateDivision(name: String, description: String): Flow<Result<Unit>> = flow {
        emit(
            divisionDataSource.postCreateDivision(
                name = name,
                description = description,
            )
        )
    }
        .flowOn(dispatcher)
        .asResult()

}