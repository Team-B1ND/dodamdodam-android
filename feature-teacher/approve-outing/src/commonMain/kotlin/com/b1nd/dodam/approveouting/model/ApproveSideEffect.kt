package com.b1nd.dodam.approveouting.model

sealed interface ApproveSideEffect {
    data object SuccessApprove : ApproveSideEffect
    data object SuccessReject : ApproveSideEffect
    data class Failed(val throwable: Throwable) : ApproveSideEffect
}
