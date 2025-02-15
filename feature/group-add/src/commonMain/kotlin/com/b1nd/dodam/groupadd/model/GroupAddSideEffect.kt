package com.b1nd.dodam.groupadd.model

sealed interface GroupAddSideEffect {
    data object SuccessAddMember : GroupAddSideEffect
    data object FailedAddMember : GroupAddSideEffect
}
