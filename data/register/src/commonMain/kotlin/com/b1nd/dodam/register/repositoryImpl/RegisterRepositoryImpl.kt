package com.b1nd.dodam.register.repositoryImpl

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.register.datasource.RegisterDataSource
import com.b1nd.dodam.register.repository.RegisterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class RegisterRepositoryImpl constructor(
    private val registerDataSource: RegisterDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : RegisterRepository {

    override suspend fun register(email: String, grade: Int, id: String, name: String, number: Int, phone: String, pw: String, room: Int): Flow<Result<Unit>> {
        return flow {
            emit(
                registerDataSource.register(
                    email = email,
                    grade = grade,
                    id = id,
                    name = name,
                    number = number,
                    phone = phone,
                    pw = pw,
                    room = room,
                ),
            )
        }.asResult().flowOn(dispatcher)
    }

    override suspend fun registerTeacher(
        id: String,
        email: String,
        name: String,
        phone: String,
        pw: String,
        position: String,
        tel: String,
    ): Flow<Result<Unit>> = flow {
        emit(
            registerDataSource.registerTeacher(
                id = id,
                email = email,
                name = name,
                phone = phone,
                pw = pw,
                position = position,
                tel = tel,
            ),
        )
    }
        .asResult()
        .flowOn(dispatcher)
}
