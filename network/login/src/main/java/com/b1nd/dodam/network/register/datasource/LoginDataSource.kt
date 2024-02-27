package com.b1nd.dodam.network.register.datasource

import com.b1nd.dodam.network.register.model.LoginResponse

interface LoginDataSource {
    suspend fun login(id: String, pw: String): LoginResponse
}
