package com.b1nd.dodam.data.login.repositoryimpl

import com.b1nd.dodam.data.login.mapper.toModel
import com.b1nd.dodam.data.login.repository.LoginRepository
import com.b1nd.dodam.model.Member
import com.b1nd.dodam.network.login.datasource.LoginDataSource
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginRepository {

    override fun login(id: String, pw: String): Flow<Member> {
        return flow {
            emit(
                loginDataSource.login(id, pw).data.toModel()
            )
        }
    }
}
