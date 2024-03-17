package com.b1nd.dodam.nightstudy

import com.b1nd.dodam.data.nightstudy.model.NightStudy
import kotlinx.collections.immutable.ImmutableList

data class NightStudyUiState(
    val nightStudy: ImmutableList<NightStudy>? = null,
    val isLoading: Boolean = false,
)
