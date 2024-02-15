package com.b1nd.dodam.data.login.repository_impl

import com.b1nd.dodam.data.login.mapper.toModel
import com.b1nd.dodam.data.login.repository.LoginRepository
import com.b1nd.dodam.model.Member
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.login.datasource.LoginDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) :LoginRepository {
    override fun login(id: String, pw: String): Flow<Response<Member>> {
        return loginDataSource.login(id, pw).map {
            Response(it.data.toModel(), it.message, it.status)
        }
    }
}