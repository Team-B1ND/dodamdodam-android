package com.b1nd.dodam.member.repository

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.member.MemberRepository
import com.b1nd.dodam.member.datasource.MemberDataSource
import com.b1nd.dodam.member.model.ActiveStatus
import com.b1nd.dodam.member.model.MyInfo
import com.b1nd.dodam.member.model.toModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class MemberRepositoryImpl(
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

    override suspend fun getMemberActiveAll(): Flow<Result<ImmutableList<MyInfo>>> {
        return flow {
            emit(network.getMemberAll(ActiveStatus.ACTIVE.name).map { it.toModel() }.toImmutableList())
        }
            .asResult()
            .flowOn(dispatcher)
    }
}
