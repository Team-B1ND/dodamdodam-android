package com.b1nd.dodam.data.login.repository

import com.b1nd.dodam.model.Member
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(id: String, pw: String): Flow<Member>
}