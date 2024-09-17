package com.b1nd.dodam.outing.model

import com.b1nd.dodam.data.outing.model.Outing
import kotlinx.collections.immutable.ImmutableList

data class OutState(
    val outPendingUiState: OutPendingUiState = OutPendingUiState.Loading,
)

sealed interface OutPendingUiState {
    data class Success(
        val outPendingCount: Int,
        val sleepoverPendingCount: Int,
        val outMembers: ImmutableList<Outing>,
        val sleepoverMembers: ImmutableList<Outing>,
    ) : OutPendingUiState
    data object Loading : OutPendingUiState
    data object Error : OutPendingUiState
}
