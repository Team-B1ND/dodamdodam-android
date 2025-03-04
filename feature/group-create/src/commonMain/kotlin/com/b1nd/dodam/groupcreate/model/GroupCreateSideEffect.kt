package com.b1nd.dodam.groupcreate.model

sealed interface GroupCreateSideEffect {
    data object SuccessGroupCreate : GroupCreateSideEffect
    data class FailedGroupCreate(val error: Throwable) : GroupCreateSideEffect
}
