package com.b1nd.dodam.approve

import com.b1nd.dodam.data.outing.model.Outing
import kotlinx.collections.immutable.ImmutableList


data class ApproveOutState(
    val outPendingUiState: OutPendingUiState = OutPendingUiState.Loading,
)


sealed interface OutPendingUiState {
    data class Success(
        val outMembers: ImmutableList<Outing>,
        val sleepoverMembers: ImmutableList<Outing>
    ) : OutPendingUiState
    data object Loading : OutPendingUiState
    data object Error : OutPendingUiState
}
