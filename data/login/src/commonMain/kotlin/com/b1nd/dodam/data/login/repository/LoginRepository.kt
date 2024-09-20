package com.b1nd.dodam.data.login.repository

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.login.model.Member
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(id: String, pw: String): Flow<Result<Member>>
}
