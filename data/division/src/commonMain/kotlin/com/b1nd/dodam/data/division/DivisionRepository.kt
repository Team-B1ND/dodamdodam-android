package com.b1nd.dodam.data.division

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.division.model.DivisionInfo
import com.b1nd.dodam.data.division.model.DivisionMember
import com.b1nd.dodam.data.division.model.DivisionOverview
import com.b1nd.dodam.data.division.model.DivisionPermission
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface DivisionRepository {
    suspend fun getAllDivisions(lastId: Int, limit: Int, keyword: String): Flow<Result<ImmutableList<DivisionOverview>>>
    suspend fun getMyDivisions(lastId: Int, limit: Int, keyword: String): Flow<Result<ImmutableList<DivisionOverview>>>
    suspend fun getDivision(id: Int): Flow<Result<DivisionInfo>>
    suspend fun getDivisionMembers(id: Int, status: Status): Flow<Result<ImmutableList<DivisionMember>>>
    suspend fun getDivisionMembersCnt(id: Int, status: Status): Flow<Result<Int>>
    suspend fun deleteDivision(divisionId: Int): Flow<Result<Unit>>
    suspend fun deleteDivisionMembers(divisionId: Int, memberId: List<Int>): Flow<Result<Unit>>
    suspend fun postDivisionApplyRequest(divisionId: Int): Flow<Result<Unit>>
    suspend fun postDivisionAddMembers(divisionId: Int, memberId: List<String>): Flow<Result<Unit>>
    suspend fun postCreateDivision(name: String, description: String): Flow<Result<Unit>>
    suspend fun patchDivisionMembers(divisionId: Int, memberId: List<Int>, status: Status): Flow<Result<Unit>>
    suspend fun patchDivisionMemberPermission(divisionId: Int, memberId: Int, permission: DivisionPermission): Flow<Result<Unit>>
}
