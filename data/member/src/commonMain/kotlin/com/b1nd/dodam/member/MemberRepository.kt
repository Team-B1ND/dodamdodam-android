package com.b1nd.dodam.member

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.member.model.MyInfo
import com.b1nd.dodam.network.core.model.NetworkStatus
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    suspend fun getMyInfo(): Flow<Result<MyInfo>>
    suspend fun deactivation(): Flow<Result<Unit>>
    suspend fun getMemberActiveAll(): Flow<Result<ImmutableList<MyInfo>>>
}
