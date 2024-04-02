package com.b1nd.dodam.network.point.datasource

import com.b1nd.dodam.network.point.model.PointResponse
import kotlinx.collections.immutable.ImmutableList

interface PointDataSource {
    suspend fun getMyPoint(type: String): ImmutableList<PointResponse>
}
