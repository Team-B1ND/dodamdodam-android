package com.b1nd.dodam.network.login.datasource

import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.login.mdoel.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginDataSource {
    fun login(id: String, password: String): Flow<Response<LoginResponse>>
}
