package com.b1nd.dodam.outing.model

data class OutState(
    val outPendingUiState: OutPendingUiState = OutPendingUiState.Loading,
)

sealed interface OutPendingUiState {
    data class Success(
        val outPendingCount: Int,
        val sleepoverPendingCount: Int,
    ) : OutPendingUiState
    data object Loading : OutPendingUiState
    data object Error : OutPendingUiState
}
