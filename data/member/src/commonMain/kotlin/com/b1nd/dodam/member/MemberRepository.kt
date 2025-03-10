package com.b1nd.dodam.member

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.core.model.Member
import com.b1nd.dodam.member.model.MemberInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    suspend fun getMyInfo(): Flow<Result<MemberInfo>>
    suspend fun deactivation(): Flow<Result<Unit>>
    suspend fun getMemberActiveAll(): Flow<Result<ImmutableList<MemberInfo>>>
    suspend fun editMemberInfo(name: String, email: String, phone: String, profileImage: String?): Flow<Result<Unit>>
    suspend fun getChildren(code: String): Flow<Result<Member>>
    suspend fun getAuthCode(type: String, identifier: String): Flow<Result<Unit>>
    suspend fun verifyAuthCode(type: String, identifier: String, authCode: String, userAgent: String): Flow<Result<Unit>>
}
