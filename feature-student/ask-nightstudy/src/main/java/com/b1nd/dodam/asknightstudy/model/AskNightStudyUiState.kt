package com.b1nd.dodam.asknightstudy.model

import com.b1nd.dodam.data.nightstudy.model.NightStudyStudent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class AskNightStudyUiState(
    val isLoading: Boolean = false,
    val message: String = "",
    val students: ImmutableList<NightStudyStudent> = persistentListOf(),
)
