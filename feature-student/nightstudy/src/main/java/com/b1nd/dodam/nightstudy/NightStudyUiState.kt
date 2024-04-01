package com.b1nd.dodam.nightstudy

import com.b1nd.dodam.data.nightstudy.model.NightStudy
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class NightStudyUiState(
    val nightStudy: ImmutableList<NightStudy> = persistentListOf(),
    val isLoading: Boolean = false,
)
