package com.b1nd.dodam.data.division

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.division.model.DivisionInfo
import com.b1nd.dodam.data.division.model.DivisionMember
import com.b1nd.dodam.data.division.model.DivisionOverview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow


interface DivisionRepository {
    suspend fun getAllDivisions(lastId: Int, limit: Int, keyword: String): Flow<Result<ImmutableList<DivisionOverview>>>
    suspend fun getMyDivisions(lastId: Int, limit: Int, keyword: String): Flow<Result<ImmutableList<DivisionOverview>>>
    suspend fun getDivision(id: Int): Flow<Result<DivisionInfo>>
    suspend fun getDivisionMembers(id: Int, status: Status): Flow<Result<ImmutableList<DivisionMember>>>
    suspend fun getDivisionMembersCnt(id: Int, status: Status): Flow<Result<Int>>
    suspend fun deleteDivisionMembers(divisionId: Int, memberId: List<Int>): Flow<Result<Unit>>
    suspend fun postDivisionApplyRequest(divisionId: Int): Flow<Result<Unit>>
}