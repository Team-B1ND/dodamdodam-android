package com.b1nd.dodam.data.division.model

import com.b1nd.dodam.network.division.response.DivisionInfoResponse

data class DivisionInfo(
    val id: Int,
    val divisionName: String,
    val description: String,
    val myPermission: DivisionPermission?,
)

internal fun DivisionInfoResponse.toModel() =
    DivisionInfo(
        id = id,
        divisionName = divisionName,
        description = description,
        myPermission = when (myPermission) {
            "READER" -> DivisionPermission.READER
            "WRITER" -> DivisionPermission.WRITER
                "ADMIN" -> DivisionPermission.ADMIN
                else -> null
        }
    )