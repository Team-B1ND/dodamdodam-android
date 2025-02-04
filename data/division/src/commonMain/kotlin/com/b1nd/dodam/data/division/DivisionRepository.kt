package com.b1nd.dodam.data.division

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.division.model.DivisionOverview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow


interface DivisionRepository {
    suspend fun getAllDivisions(lastId: Int, limit: Int): Flow<Result<ImmutableList<DivisionOverview>>>
    suspend fun getMyDivisions(lastId: Int, limit: Int): Flow<Result<ImmutableList<DivisionOverview>>>
}