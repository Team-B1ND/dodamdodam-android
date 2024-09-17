package com.b1nd.dodam.network.point.datasource

import com.b1nd.dodam.network.point.model.PointReasonResponse
import com.b1nd.dodam.network.point.model.PointResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate

interface PointDataSource {
    suspend fun getMyPoint(type: String): ImmutableList<PointResponse>

    suspend fun getScoreReason(type: String): ImmutableList<PointReasonResponse>

    suspend fun postGivePoint(issueAt: LocalDate, reasonId: Int, studentIds: List<Int>)
}
