package com.b1nd.dodam.group.model

sealed interface GroupSideEffect {
    data object FailedLoad: GroupSideEffect
}