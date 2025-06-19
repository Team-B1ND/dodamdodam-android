package com.b1nd.dodam.network.login.datasource

import com.b1nd.dodam.network.login.model.LoginResponse

interface LoginDataSource {
    suspend fun login(id: String, pw: String, pushToken: String): LoginResponse
    suspend fun qrLogin(code: String, access: String, refresh: String, clientId: String, word: String): LoginResponse
    fun clearToken()
}
