package com.b1nd.dodam.register.state

interface InfoSideEffect {
    data object NavigateToAuth : InfoSideEffect
    data object SuccessGetAuthPhoneCode : InfoSideEffect
    data object SuccessGetAuthEmailCode : InfoSideEffect
    data object SuccessVerifyAuthPhoneCode : InfoSideEffect
    data class FiledVerifyAuthCode(val type: String): InfoSideEffect
}