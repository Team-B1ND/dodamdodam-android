package com.b1nd.dodam.club.repository

import com.b1nd.dodam.club.model.Club
import com.b1nd.dodam.club.model.ClubJoin
import com.b1nd.dodam.club.model.ClubJoinResponse
import com.b1nd.dodam.club.model.ClubMember
import com.b1nd.dodam.club.model.ClubMemberStudent
import com.b1nd.dodam.club.model.ClubMyJoined
import com.b1nd.dodam.club.model.ClubState
import com.b1nd.dodam.club.model.request.ClubJoinRequest
import com.b1nd.dodam.common.result.Result
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface ClubRepository {
    suspend fun postClubJoinRequestsAllow(id: Int): Flow<Result<Unit>>
    suspend fun postClubJoinRequests(requests: List<ClubJoinRequest>): Flow<Result<Unit>>
    suspend fun deleteClubJoinRequest(id: Int): Flow<Result<Unit>>
    suspend fun getDetailClub(id: Int): Flow<Result<Club>>
    suspend fun getClubJoinRequestReceived(): Flow<Result<ImmutableList<ClubJoin>>>
    suspend fun getClubLeader(id: Int): Flow<Result<ClubMemberStudent>>
    suspend fun getClubMember(id: Int): Flow<Result<ClubMember>>
    suspend fun getClubList(): Flow<Result<ImmutableList<Club>>>
    suspend fun getClubJoined(): Flow<Result<ImmutableList<ClubMyJoined>>>
    suspend fun getClubMyCreated(): Flow<Result<ImmutableList<Club>>>
    suspend fun patchClubState(clubIds: ImmutableList<Int>, status: ClubState, reason: String?): Flow<Result<Unit>>
    suspend fun getClubMyJoinRequest(): Flow<Result<ImmutableList<ClubJoinResponse>>>
}
