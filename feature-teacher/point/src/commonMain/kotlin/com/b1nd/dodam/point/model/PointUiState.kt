package com.b1nd.dodam.point.model

import com.b1nd.dodam.data.point.model.PointReason
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class PointUiState(
    val loading: Boolean = true,
    val students: ImmutableList<PointStudentModel> = persistentListOf(),
    val reasons: ImmutableList<PointReason> = persistentListOf()
)