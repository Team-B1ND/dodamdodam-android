package com.b1nd.dodam.network.nightstudy.model

import com.b1nd.dodam.network.core.model.NetworkPlace
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class NightStudyRequest(
    val place: NetworkPlace,
    val content: String,
    val doNeedPhone: Boolean,
    val reasonForPhone: String?,
    val startAt: LocalDate,
    val endAt: LocalDate,
)
