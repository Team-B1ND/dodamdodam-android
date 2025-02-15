package com.b1nd.dodam.groupdetail.model

sealed interface GroupDetailSideEffect {
    data object SuccessEditPermission : GroupDetailSideEffect
    data class FailedEditPermission(val throwable: Throwable) : GroupDetailSideEffect
    data object SuccessDeleteGroup : GroupDetailSideEffect
    data class FailedDeleteGroup(val throwable: Throwable) : GroupDetailSideEffect
    data class FailedKickMember(val throwable: Throwable) : GroupDetailSideEffect
}
