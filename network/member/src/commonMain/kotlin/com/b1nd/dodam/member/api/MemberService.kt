package com.b1nd.dodam.member.api

import com.b1nd.dodam.member.datasource.MemberDataSource
import com.b1nd.dodam.member.model.EditMemberInfoRequest
import com.b1nd.dodam.member.model.GetAuthCodeRequest
import com.b1nd.dodam.member.model.MemberInfoResponse
import com.b1nd.dodam.member.model.VerifyAuthCodeRequest
import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.model.MemberResponse
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.defaultSafeRequest
import com.b1nd.dodam.network.core.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.headers

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

    override suspend fun getMemberAll(status: String): List<MemberInfoResponse> {
        return safeRequest {
            client.get(DodamUrl.Member.STATUS) {
                parameter("status", status)
            }.body()
        }
    }

    override suspend fun editMemberInfo(name: String, email: String, phone: String, profileImage: String?) {
        return defaultSafeRequest {
            client.patch(DodamUrl.Member.EDIT) {
                setBody(
                    EditMemberInfoRequest(
                        name = name,
                        email = email,
                        phone = phone,
                        profileImage = profileImage,
                    ),
                )
            }.body<DefaultResponse>()
        }
    }
    override suspend fun getChildren(code: String): MemberResponse = safeRequest {
        client.get("${DodamUrl.Member.CODE}/$code") {
        }.body<Response<MemberResponse>>()
    }

    override suspend fun getAuthCode(type: String, identifier: String) {
        return defaultSafeRequest {
            client.post("${DodamUrl.Member.AUTH_CODE}/$type") {
                setBody(
                    GetAuthCodeRequest(identifier),
                )
            }.body<DefaultResponse>()
        }
    }

    override suspend fun verifyAuthCode(type: String, identifier: String, authCode: String, userAgent: String) {
        return defaultSafeRequest {
            client.post("${DodamUrl.Member.AUTH_CODE}/$type/verify") {
                headers {
                    append("User-Agent", userAgent)
                }
                setBody(
                    VerifyAuthCodeRequest(identifier, authCode),
                )
            }.body<DefaultResponse>()
        }
    }
}
