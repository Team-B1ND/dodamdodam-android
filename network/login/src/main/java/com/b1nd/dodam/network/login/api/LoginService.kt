package com.b1nd.dodam.network.login.api

import android.util.Log
import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.login.datasource.LoginDataSource
import com.b1nd.dodam.network.login.model.LoginRequest
import com.b1nd.dodam.network.login.model.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

internal class LoginService @Inject constructor(
    private val client: HttpClient,
) : LoginDataSource {
    override suspend fun login(id: String, pw: String): LoginResponse {
        return safeRequest {
            client.post(DodamUrl.Auth.LOGIN) {
                setBody(LoginRequest(id, pw))
            }.body<Response<LoginResponse>>()
        }
    }
}
