package com.b1nd.dodam.club.repository

import com.b1nd.dodam.club.model.Club
import com.b1nd.dodam.club.model.ClubJoin
import com.b1nd.dodam.club.model.ClubMember
import com.b1nd.dodam.common.result.Result
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface ClubRepository {
    suspend fun postClubJoinRequests(id: Int): Flow<Result<Unit>>
    suspend fun deleteClubJoinRequest(id: Int): Flow<Result<Unit>>
    suspend fun getDetailClub(id: Int): Flow<Result<Club>>
    suspend fun getClubJoinRequestReceived(): Flow<Result<ImmutableList<ClubJoin>>>
    suspend fun getClubLeader(id: Int): Flow<Result<ClubMember>>
    suspend fun getClubAllowMember(id: Int): Flow<Result<ImmutableList<ClubMember>>>
    suspend fun getClubAllMember(id: Int): Flow<Result<ImmutableList<ClubMember>>>
    suspend fun getClubList(): Flow<Result<ImmutableList<Club>>>
}
