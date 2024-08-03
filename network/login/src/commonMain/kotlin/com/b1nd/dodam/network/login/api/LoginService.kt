package com.b1nd.dodam.network.login.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.login.datasource.LoginDataSource
import com.b1nd.dodam.network.login.model.LoginRequest
import com.b1nd.dodam.network.login.model.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

internal class LoginService(
    private val client: HttpClient,
) : LoginDataSource {
    override suspend fun login(id: String, pw: String): LoginResponse {
        return safeRequest {
            client.post(DodamUrl.Auth.LOGIN) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(LoginRequest(id, pw))
            }.body<Response<LoginResponse>>()
        }
    }
}
