package com.b1nd.dodam.club.api

import com.b1nd.dodam.club.datasource.ClubDataSource
import com.b1nd.dodam.club.model.ClubJoinResponse
import com.b1nd.dodam.club.model.ClubMemberResponse
import com.b1nd.dodam.club.model.ClubResponse
import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import kotlinx.collections.immutable.ImmutableList
import io.ktor.client.request.get
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import io.ktor.client.request.delete
import io.ktor.client.request.post
import kotlinx.collections.immutable.toImmutableList

class ClubService(
    private val client: HttpClient,
) : ClubDataSource {
    override suspend fun postClubJoinRequests(id: Int) {
        defaultSafeRequest {
            client.post(DodamUrl.Club.JOIN_REQUEST + "/$id").body<DefaultResponse>()
        }
    }

    override suspend fun deleteClubJoinRequest(id: Int) {
        defaultSafeRequest {
            client.delete(DodamUrl.Club.JOIN_REQUEST + "/$id").body<DefaultResponse>()
        }
    }

    override suspend fun getDetailClub(id: Int): ClubResponse {
        return safeRequest {
            client.get(DodamUrl.CLUB + "/$id").body<Response<ClubResponse>>()
        }
    }

    override suspend fun getClubJoinRequestReceived(): ImmutableList<ClubJoinResponse> {
        return safeRequest {
            client.get(DodamUrl.Club.JOIN_REQUEST + "/received").body<Response<List<ClubJoinResponse>>>()
        }.toImmutableList()
    }


    override suspend fun getClubLeader(id: Int): ClubMemberResponse {
        return safeRequest {
            client.get(DodamUrl.CLUB + "/$id/leader").body<Response<ClubMemberResponse>>()
        }
    }

    override suspend fun getClubAllowMember(id: Int): ImmutableList<ClubMemberResponse> {
        return safeRequest {
            client.get(DodamUrl.CLUB + "/$id/members").body<Response<List<ClubMemberResponse>>>()
        }.toImmutableList()
    }

    override suspend fun getClubAllMember(id: Int): ImmutableList<ClubMemberResponse> {
        return safeRequest {
            client.get(DodamUrl.CLUB + "/$id/all-members").body<Response<List<ClubMemberResponse>>>()
        }.toImmutableList()
    }

    override suspend fun getClubList(): ImmutableList<ClubResponse> {
        return safeRequest {
            client.get(DodamUrl.CLUB).body<Response<List<ClubResponse>>>()
        }.toImmutableList()
    }
}