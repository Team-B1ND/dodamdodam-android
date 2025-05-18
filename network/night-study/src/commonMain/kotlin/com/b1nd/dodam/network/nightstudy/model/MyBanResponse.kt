package com.b1nd.dodam.network.nightstudy.model

import com.b1nd.dodam.network.core.model.StudentImageResponse
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class MyBanResponse(
    val id: Long?,
    val student: StudentImageResponse?,
    val banReason: String?,
    val started: LocalDate?,
    val ended: LocalDate?,
)
