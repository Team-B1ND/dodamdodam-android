package com.b1nd.dodam.club.datasource

import com.b1nd.dodam.club.model.ClubJoinResponse
import com.b1nd.dodam.club.model.ClubMemberResponse
import com.b1nd.dodam.club.model.ClubResponse
import kotlinx.collections.immutable.ImmutableList

interface ClubDataSource {
    suspend fun postClubJoinRequestsAllow(id: Int)
    suspend fun postClubJoinRequests(clubId: Int, clubPriority: String, introduce: String)
    suspend fun deleteClubJoinRequests(id: Int)
    suspend fun getDetailClub(id: Int): ClubResponse
    suspend fun getClubJoinRequestReceived(): ImmutableList<ClubJoinResponse>
    suspend fun getClubLeader(id: Int): ClubMemberResponse
    suspend fun getClubMember(id: Int): ImmutableList<ClubMemberResponse>
    suspend fun getClubList(): ImmutableList<ClubResponse>
    suspend fun getClubJoined(): ImmutableList<ClubResponse>
    suspend fun getClubMyCreated(): ImmutableList<ClubResponse>
    suspend fun patchClubState(clubIds: ImmutableList<Int>, status: String, reason: String?)
}
