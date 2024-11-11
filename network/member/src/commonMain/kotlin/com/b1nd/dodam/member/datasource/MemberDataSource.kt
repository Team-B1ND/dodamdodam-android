package com.b1nd.dodam.member.datasource

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.member.model.MemberInfoResponse
import com.b1nd.dodam.network.core.model.DefaultResponse
import kotlinx.coroutines.flow.Flow

interface MemberDataSource {
    suspend fun getMyInfo(): MemberInfoResponse
    suspend fun deactivation()
    suspend fun getMemberAll(status: String): List<MemberInfoResponse>
    suspend fun editMemberInfo(name: String, email: String, phone: String, profileImage: String?)
}
