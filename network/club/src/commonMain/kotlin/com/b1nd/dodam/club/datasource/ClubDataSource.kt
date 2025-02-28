package com.b1nd.dodam.club.datasource

import com.b1nd.dodam.club.model.ClubJoinResponse
import com.b1nd.dodam.club.model.ClubMemberResponse
import com.b1nd.dodam.club.model.ClubResponse
import kotlinx.collections.immutable.ImmutableList

interface ClubDataSource {
    suspend fun postClubJoinRequests(id: Int)
    suspend fun deleteClubJoinRequest(id: Int)
    suspend fun getDetailClub(id: Int): ClubResponse
    suspend fun getClubJoinRequestReceived(): ImmutableList<ClubJoinResponse>
    suspend fun getClubLeader(id: Int): ClubMemberResponse
    suspend fun getClubAllowMember(id: Int): ImmutableList<ClubMemberResponse>
    suspend fun getClubAllMember(id: Int): ImmutableList<ClubMemberResponse>
    suspend fun getClubList() : ImmutableList<ClubResponse>
}