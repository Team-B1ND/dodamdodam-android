package com.b1nd.dodam.club.repositoryimpl

import com.b1nd.dodam.club.datasource.ClubDataSource
import com.b1nd.dodam.club.model.Club
import com.b1nd.dodam.club.model.ClubJoin
import com.b1nd.dodam.club.model.ClubMember
import com.b1nd.dodam.club.model.ClubMemberStudent
import com.b1nd.dodam.club.model.ClubState
import com.b1nd.dodam.club.model.toModel
import com.b1nd.dodam.club.repository.ClubRepository
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

internal class ClubRepositoryImpl(
    private val network: ClubDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : ClubRepository {
    override suspend fun postClubJoinRequestsAllow(id: Int): Flow<Result<Unit>> {
        return flow {
            emit(network.postClubJoinRequestsAllow(id))
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override suspend fun postClubJoinRequests(clubId: Int, clubPriority: String, introduce: String): Flow<Result<Unit>> {
        return flow {
            emit(
                network.postClubJoinRequests(
                    clubId = clubId,
                    clubPriority = clubPriority,
                    introduce = introduce,
                ),
            )
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override suspend fun deleteClubJoinRequest(id: Int): Flow<Result<Unit>> {
        return flow {
            emit(network.deleteClubJoinRequests(id))
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

    override suspend fun getClubLeader(id: Int): Flow<Result<ClubMemberStudent>> {
        return flow {
            emit(
                network.getClubLeader(id).toModel(),
            )
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override suspend fun getClubMember(id: Int): Flow<Result<ClubMember>> {
        return flow {
            emit(
                network.getClubMember(id).toModel(),
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

    override suspend fun getClubJoined(): Flow<Result<ImmutableList<Club>>> {
        return flow {
            emit(network.getClubJoined().map { it.toModel() }.toImmutableList())
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override suspend fun getClubMyCreated(): Flow<Result<ImmutableList<Club>>> {
        return flow {
            emit(network.getClubMyCreated().map { it.toModel() }.toImmutableList())
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override suspend fun patchClubState(clubIds: ImmutableList<Int>, status: ClubState, reason: String?): Flow<Result<Unit>> {
        return flow {
            emit(
                network.patchClubState(
                    clubIds = clubIds,
                    status = status.toString(),
                    reason = reason,
                ),
            )
        }
            .asResult()
            .flowOn(dispatcher)
    }
}
