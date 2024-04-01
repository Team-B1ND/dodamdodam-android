package com.b1nd.dodam.data.outing

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.outing.model.Point
import com.b1nd.dodam.data.outing.model.PointType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface PointRepository {
    fun getMyOut(type: PointType): Flow<Result<ImmutableList<Point>>>
}
