package com.b1nd.dodam.groupdetail.model

sealed interface GroupDetailSideEffect {
    data object SuccessEditPermission: GroupDetailSideEffect
    data class FailedEditPermission(val throwable: Throwable): GroupDetailSideEffect
}