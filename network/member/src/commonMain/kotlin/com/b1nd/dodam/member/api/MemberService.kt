package com.b1nd.dodam.member.api

import com.b1nd.dodam.member.datasource.MemberDataSource
import com.b1nd.dodam.member.model.MemberInfoResponse
import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.network.core.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch

internal class MemberService(
    private val client: HttpClient,
) : MemberDataSource {
    override suspend fun getMyInfo(): MemberInfoResponse {
        return safeRequest {
            client.get(DodamUrl.Member.MY)
                .body<Response<MemberInfoResponse>>()
        }
    }

    override suspend fun deactivation() {
        return defaultSafeRequest {
            client.patch(DodamUrl.Member.DEACTIVATION)
                .body()
        }
    }

    override suspend fun getMemberAll(
        status: String
    ): List<MemberInfoResponse> {
        return safeRequest {
            client.get(DodamUrl.Member.STATUS) {
                parameter("status", status)
            }.body()
        }
    }
}
