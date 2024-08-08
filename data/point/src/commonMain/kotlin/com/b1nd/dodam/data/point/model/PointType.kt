package com.b1nd.dodam.data.point.model

import com.b1nd.dodam.network.point.model.NetworkPointType

enum class PointType(val type: String) {
    DORMITORY("기숙사"),
    SCHOOL("학교"),
}

internal fun NetworkPointType.toModel() = when (this) {
    NetworkPointType.DORMITORY -> PointType.DORMITORY
    NetworkPointType.SCHOOL -> PointType.SCHOOL
}
