package com.b1nd.dodam.member.api

import com.b1nd.dodam.member.datasource.MemberDataSource
import com.b1nd.dodam.member.model.MyInfoResponse
import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.network.core.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch

internal class MemberService(
    private val client: HttpClient,
) : MemberDataSource {
    override suspend fun getMyInfo(): MyInfoResponse {
        return safeRequest {
            client.get(DodamUrl.Member.MY)
                .body<Response<MyInfoResponse>>()
        }
    }

    override suspend fun deactivation() {
        return defaultSafeRequest {
            client.patch(DodamUrl.Member.DEACTIVATION)
                .body()
        }
    }
}
