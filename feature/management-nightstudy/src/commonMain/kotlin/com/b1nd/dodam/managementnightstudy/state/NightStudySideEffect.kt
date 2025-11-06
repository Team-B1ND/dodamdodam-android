package com.b1nd.dodam.managementnightstudy.state

sealed interface NightStudySideEffect {
    data object SuccessBan : NightStudySideEffect
    data class Failed(val throwable: Throwable) : NightStudySideEffect
}
