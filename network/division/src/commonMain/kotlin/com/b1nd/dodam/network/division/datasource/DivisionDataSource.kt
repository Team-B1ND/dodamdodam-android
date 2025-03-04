package com.b1nd.dodam.network.division.datasource

import com.b1nd.dodam.network.division.response.DivisionInfoResponse
import com.b1nd.dodam.network.division.response.DivisionMemberResponse
import com.b1nd.dodam.network.division.response.DivisionOverviewResponse

interface DivisionDataSource {
    suspend fun getAllDivisions(lastId: Int, limit: Int, keyword: String): List<DivisionOverviewResponse>
    suspend fun getMyDivisions(lastId: Int, limit: Int, keyword: String): List<DivisionOverviewResponse>
    suspend fun getDivision(id: Int): DivisionInfoResponse
    suspend fun getDivisionMembers(id: Int, status: String): List<DivisionMemberResponse>
    suspend fun getDivisionMembersCnt(id: Int, status: String): Int
    suspend fun deleteDivision(divisionId: Int)
    suspend fun deleteDivisionMembers(divisionId: Int, memberId: List<Int>)
    suspend fun postDivisionApplyRequest(divisionId: Int)
    suspend fun postDivisionAddMembers(divisionId: Int, memberId: List<String>)
    suspend fun postCreateDivision(name: String, description: String)
    suspend fun patchDivisionMembers(divisionId: Int, memberId: List<Int>, status: String)
    suspend fun patchDivisionMemberPermission(divisionId: Int, memberId: Int, permission: String)
}
