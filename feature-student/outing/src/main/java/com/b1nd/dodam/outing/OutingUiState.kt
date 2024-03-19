package com.b1nd.dodam.outing

import com.b1nd.dodam.data.outing.model.Outing
import kotlinx.collections.immutable.ImmutableList

data class OutingUiState(
    val outings: ImmutableList<Outing>? = null,
    val isLoading: Boolean = false,
)
