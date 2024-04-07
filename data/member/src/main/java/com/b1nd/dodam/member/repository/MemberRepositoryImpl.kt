package com.b1nd.dodam.member.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.member.MemberRepository
import com.b1nd.dodam.member.datasource.MemberDataSource
import com.b1nd.dodam.member.model.MyInfo
import com.b1nd.dodam.member.model.toModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class MemberRepositoryImpl @Inject constructor(
    private val network: MemberDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : MemberRepository {
    override suspend fun getMyInfo(): Flow<Result<MyInfo>> {
        return flow {
            emit(network.getMyInfo().toModel())
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override suspend fun deactivation(): Flow<Result<Unit>> {
        return flow {
            emit(network.deactivation())
        }
            .asResult()
            .flowOn(dispatcher)
    }
}
