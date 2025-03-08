package com.b1nd.dodam.club.model


sealed interface ApplySideEffect {
    data object SuccessApply : ApplySideEffect
    data object SuccessReject : ApplySideEffect
    data class Failed(val throwable: Throwable) : ApplySideEffect
}
