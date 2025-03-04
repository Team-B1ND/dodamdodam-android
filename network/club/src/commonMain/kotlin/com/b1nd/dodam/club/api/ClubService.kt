package com.b1nd.dodam.club.api

import com.b1nd.dodam.club.datasource.ClubDataSource
import com.b1nd.dodam.club.model.ClubJoinResponse
import com.b1nd.dodam.club.model.ClubMemberResponse
import com.b1nd.dodam.club.model.ClubResponse
import com.b1nd.dodam.club.model.request.ClubJoinRequest
import com.b1nd.dodam.club.model.request.ClubStateRequest
import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.network.core.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class ClubService(
    private val client: HttpClient,
) : ClubDataSource {
    override suspend fun postClubJoinRequestsAllow(id: Int) {
        defaultSafeRequest {
            client.post(DodamUrl.Club.JOIN_REQUEST + "/$id").body<DefaultResponse>()
        }
    }

    override suspend fun postClubJoinRequests(clubId: Int, clubPriority: String, introduce: String) {
        defaultSafeRequest {
            client.post(DodamUrl.Club.JOIN_REQUEST) {
                contentType(ContentType.Application.Json)
                setBody(
                    ClubJoinRequest(
                        clubId = clubId,
                        clubPriority = clubPriority,
                        introduction = introduce,
                    ),
                )
            }.body<DefaultResponse>()
        }
    }

    override suspend fun deleteClubJoinRequests(id: Int) {
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
            client.get(DodamUrl.Club.JOIN_REQUEST + "/received")
                .body<Response<List<ClubJoinResponse>>>()
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
            client.get(DodamUrl.CLUB + "/$id/all-members")
                .body<Response<List<ClubMemberResponse>>>()
        }.toImmutableList()
    }

    override suspend fun getClubList(): ImmutableList<ClubResponse> {
        return safeRequest {
            client.get(DodamUrl.CLUB).body<Response<List<ClubResponse>>>()
        }.toImmutableList()
    }

    override suspend fun getClubJoined(): ImmutableList<ClubResponse> {
        return safeRequest {
            client.get(DodamUrl.CLUB + "/joined").body<Response<List<ClubResponse>>>()
        }.toImmutableList()
    }

    override suspend fun getClubMyCreated(): ImmutableList<ClubResponse> {
        return safeRequest {
            client.get(DodamUrl.CLUB + "/my").body<Response<List<ClubResponse>>>()
        }.toImmutableList()
    }

    override suspend fun patchClubState(clubIds: ImmutableList<Int>, status: String, reason: String?) {
        defaultSafeRequest {
            client.patch(DodamUrl.CLUB + "/state") {
                contentType(ContentType.Application.Json)
                setBody(
                    ClubStateRequest(
                        clubIds = clubIds,
                        status = status,
                        reason = reason,
                    ),
                )
            }.body<DefaultResponse>()
        }
    }
}
