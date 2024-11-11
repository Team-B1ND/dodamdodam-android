package com.b1nd.dodam.editmemberinfo.model


sealed interface EditMemberInfoSideEffect {
    data object SuccessEditMemberInfo : EditMemberInfoSideEffect
}