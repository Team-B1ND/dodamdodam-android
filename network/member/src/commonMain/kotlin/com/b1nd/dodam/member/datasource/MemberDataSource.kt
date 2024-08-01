package com.b1nd.dodam.member.datasource

import com.b1nd.dodam.member.model.MyInfoResponse

interface MemberDataSource {
    suspend fun getMyInfo(): MyInfoResponse
    suspend fun deactivation()
}
