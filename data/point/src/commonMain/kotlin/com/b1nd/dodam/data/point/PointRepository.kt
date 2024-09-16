package com.b1nd.dodam.data.point

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.point.model.Point
import com.b1nd.dodam.data.point.model.PointReason
import com.b1nd.dodam.data.point.model.PointType
import com.b1nd.dodam.network.point.model.PointReasonResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface PointRepository {
    fun getMyOut(): Flow<Result<ImmutableList<Point>>>

    suspend fun getAllScoreReason(): Flow<Result<ImmutableList<PointReason>>>

    suspend fun postGivePoint(
        issueAt: LocalDate,
        reasonId: Int,
        studentIds: List<Int>
    ): Flow<Result<Unit>>
}
