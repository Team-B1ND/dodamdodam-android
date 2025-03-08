package com.b1nd.dodam.club.datasource

import com.b1nd.dodam.club.model.ClubJoinResponse
import com.b1nd.dodam.club.model.ClubMemberResponse
import com.b1nd.dodam.club.model.ClubMemberStudentResponse
import com.b1nd.dodam.club.model.ClubMyJoinedResponse
import com.b1nd.dodam.club.model.ClubResponse
import com.b1nd.dodam.club.model.request.ClubJoinRequest
import kotlinx.collections.immutable.ImmutableList

interface ClubDataSource {
    suspend fun postClubJoinRequestsAllow(id: Int)
    suspend fun postClubJoinRequests(requests: List<ClubJoinRequest>)
    suspend fun deleteClubJoinRequests(id: Int)
    suspend fun getDetailClub(id: Int): ClubResponse
    suspend fun getClubJoinRequestReceived(): ImmutableList<ClubJoinResponse>
    suspend fun getClubLeader(id: Int): ClubMemberStudentResponse
    suspend fun getClubMember(id: Int): ClubMemberResponse
    suspend fun getClubList(): ImmutableList<ClubResponse>
    suspend fun getClubJoined(): ImmutableList<ClubMyJoinedResponse>
    suspend fun getClubMyCreated(): ImmutableList<ClubResponse>
    suspend fun patchClubState(clubIds: ImmutableList<Int>, status: String, reason: String?)
    suspend fun getClubMyRequestReceived(): ImmutableList<ClubJoinResponse>
}
