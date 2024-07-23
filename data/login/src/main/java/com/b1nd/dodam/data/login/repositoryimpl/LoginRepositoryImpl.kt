package com.b1nd.dodam.data.login.repositoryimpl

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.login.model.Token
import com.b1nd.dodam.data.login.model.toModel
import com.b1nd.dodam.data.login.repository.LoginRepository
import com.b1nd.dodam.network.login.datasource.LoginDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class LoginRepositoryImpl(
    private val loginDataSource: LoginDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : LoginRepository {

    override fun login(id: String, pw: String): Flow<Result<Token>> {
        return flow {
            emit(
                loginDataSource.login(id, pw).toModel(),
            )
        }.asResult().flowOn(dispatcher)
    }
}
