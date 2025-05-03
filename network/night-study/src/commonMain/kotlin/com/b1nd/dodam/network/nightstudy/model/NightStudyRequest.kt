package com.b1nd.dodam.network.nightstudy.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class
NightStudyRequest(
    val place: String,
    val content: String,
    val doNeedPhone: Boolean,
    val reasonForPhone: String?,
    val startAt: LocalDate,
    val endAt: LocalDate,
)

@Serializable
data class
ProjectRequest(
    val type: String,
    val startAt: LocalDate,
    val endAt: LocalDate,
    val string: String,
    val title: String,
    val content: String,
    val members: List<Int>
)
