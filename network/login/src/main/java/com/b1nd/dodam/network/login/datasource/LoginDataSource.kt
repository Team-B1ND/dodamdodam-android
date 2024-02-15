package com.b1nd.dodam.network.login.datasource

import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.login.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginDataSource {
    fun login(id: String, pw: String): Flow<Response<LoginResponse>>
}
