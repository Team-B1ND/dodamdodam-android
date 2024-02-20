package com.b1nd.dodam.network.login.datasource

import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.login.model.LoginResponse

interface LoginDataSource {
    suspend fun login(id: String, pw: String): Response<LoginResponse>
}
