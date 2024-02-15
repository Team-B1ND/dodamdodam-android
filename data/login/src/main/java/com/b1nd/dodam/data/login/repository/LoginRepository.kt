package com.b1nd.dodam.data.login.repository

import com.b1nd.dodam.model.Member
import com.b1nd.dodam.network.core.model.Response
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(id: String, pw: String): Flow<Response<Member>>
}