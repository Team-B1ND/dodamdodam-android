package com.b1nd.dodam.club.model

sealed interface ClubSideEffect {
    data object SuccessApprove : ClubSideEffect
    data object SuccessReject : ClubSideEffect
    data class Failed(val throwable: Throwable) : ClubSideEffect
}
