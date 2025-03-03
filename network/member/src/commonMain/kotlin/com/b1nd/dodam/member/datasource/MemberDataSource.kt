package com.b1nd.dodam.member.datasource

import com.b1nd.dodam.member.model.MemberInfoResponse
import com.b1nd.dodam.network.core.model.MemberResponse

interface MemberDataSource {
    suspend fun getMyInfo(): MemberInfoResponse
    suspend fun deactivation()
    suspend fun getMemberAll(status: String): List<MemberInfoResponse>
    suspend fun editMemberInfo(name: String, email: String, phone: String, profileImage: String?)
    suspend fun getChildren(code: String): MemberResponse
    suspend fun getAuthCode(type: String, identifier: String)
}
