package com.b1nd.dodam.network.nightstudy.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class NightStudyRequest(
    val content: String,
    val type: String,
    val doNeedPhone: Boolean,
    val reasonForPhone: String?,
    val startAt: LocalDate,
    val endAt: LocalDate,
)

@Serializable
data class ProjectRequest(
    val type: String,
    val name: String,
    val description: String,
    val startAt: LocalDate,
    val endAt: LocalDate,
    val students: List<Int>,
)
