package com.b1nd.dodam.approve

import com.b1nd.dodam.data.outing.model.Outing
import kotlinx.collections.immutable.ImmutableList


data class ApproveOutState(
    val outPendingUiState: OutPendingUiState = OutPendingUiState.Loading,
    val detailMember: DetailMember = DetailMember()
)


sealed interface OutPendingUiState {
    data class Success(
        val outMembers: ImmutableList<Outing>,
        val sleepoverMembers: ImmutableList<Outing>
    ) : OutPendingUiState
    data object Loading : OutPendingUiState
    data object Error : OutPendingUiState
}

data class DetailMember(
    val name: String,
    val start: String,
    val end: String,
    val reason: String
){
    constructor(): this("", "", "", "")
}