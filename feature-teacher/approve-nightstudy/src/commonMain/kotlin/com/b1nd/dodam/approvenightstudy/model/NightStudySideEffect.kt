package com.b1nd.dodam.approvenightstudy.model

interface NightStudySideEffect {
    data object SuccessAllow: NightStudySideEffect
    data object SuccessReject: NightStudySideEffect
    data class Failed(val throwable: Throwable): NightStudySideEffect
}