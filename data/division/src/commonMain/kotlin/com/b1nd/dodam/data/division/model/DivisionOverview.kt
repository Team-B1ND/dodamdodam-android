package com.b1nd.dodam.data.division.model

import com.b1nd.dodam.network.division.response.DivisionOverviewResponse

data class DivisionOverview(
    val id: Int,
    val name: String
)

internal fun DivisionOverviewResponse.toModel() =
    DivisionOverview(
        id = id,
        name = name,
    )