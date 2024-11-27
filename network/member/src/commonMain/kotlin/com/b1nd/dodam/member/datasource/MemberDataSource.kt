package com.b1nd.dodam.member.datasource

import com.b1nd.dodam.member.model.MemberInfoResponse

interface MemberDataSource {
    suspend fun getMyInfo(): MemberInfoResponse
    suspend fun deactivation()
    suspend fun getMemberAll(status: String): List<MemberInfoResponse>
    suspend fun editMemberInfo(name: String, email: String, phone: String, profileImage: String?)
}
