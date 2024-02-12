package com.b1nd.dodam.network.login.api

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.login.datasource.LoginDataSource
import com.b1nd.dodam.network.login.mdoel.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class LoginService @Inject constructor(
    private val client: HttpClient,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : LoginDataSource {
    override fun login(id: String, password: String): Flow<Response<LoginResponse>> {
        return flow {
            emit(
                client.get(DodamUrl.Auth.LOGIN) {
                    parameter("id", id)
                    parameter("password", password)
                }.body<Response<LoginResponse>>(),
            )
        }.flowOn(dispatcher)
    }
}
