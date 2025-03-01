package com.b1nd.dodam.club.repositoryimpl

import com.b1nd.dodam.club.datasource.ClubDataSource
import com.b1nd.dodam.club.model.Club
import com.b1nd.dodam.club.model.ClubJoin
import com.b1nd.dodam.club.model.ClubMember
import com.b1nd.dodam.club.model.toModel
import com.b1nd.dodam.club.repository.ClubRepository
import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.asResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.b1nd.dodam.common.result.Result
import kotlinx.collections.immutable.toImmutableList

internal class ClubRepositoryImpl(
    private val network: ClubDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ClubRepository {
    override suspend fun postClubJoinRequests(id: Int): Flow<Result<Unit>> {
        return flow {
            emit(network.postClubJoinRequests(id))
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override suspend fun deleteClubJoinRequest(id: Int): Flow<Result<Unit>> {
        return flow {
            emit(network.deleteClubJoinRequest(id))
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override suspend fun getDetailClub(id: Int): Flow<Result<Club>> {
        return flow {
            emit(
                network.getDetailClub(id).toModel(),
            )
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override suspend fun getClubJoinRequestReceived(): Flow<Result<ImmutableList<ClubJoin>>> {
        return flow {
            emit(
                network.getClubJoinRequestReceived().map { it.toModel() }.toImmutableList(),
            )
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override suspend fun getClubLeader(id: Int): Flow<Result<ClubMember>> {
        return flow {
            emit(
                network.getClubLeader(id).toModel(),
            )
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override suspend fun getClubAllowMember(id: Int): Flow<Result<ImmutableList<ClubMember>>> {
        return flow {
            emit(
                network.getClubAllowMember(id).map { it.toModel() }.toImmutableList(),
            )
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override suspend fun getClubAllMember(id: Int): Flow<Result<ImmutableList<ClubMember>>> {
        return flow {
            emit(
                network.getClubAllMember(id).map { it.toModel() }.toImmutableList(),
            )
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override suspend fun getClubList(): Flow<Result<ImmutableList<Club>>> {
        return flow {
            emit(
                network.getClubList().map { it.toModel() }.toImmutableList(),
            )
        }
            .asResult()
            .flowOn(dispatcher)
    }

}