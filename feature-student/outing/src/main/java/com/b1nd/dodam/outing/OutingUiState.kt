package com.b1nd.dodam.outing

import com.b1nd.dodam.data.outing.model.Outing
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class OutingUiState(
    val outings: ImmutableList<Outing> = persistentListOf(),
    val isLoading: Boolean = false
)
