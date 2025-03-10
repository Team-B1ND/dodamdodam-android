package com.b1nd.dodam.register.state
sealed interface InfoEvent {
    data object SuccessGetAuthPhoneCode : InfoEvent
    data object SuccessVerifyAuthPhoneCode : InfoEvent
    data object FiledVerifyAuthCode : InfoEvent
    data object FiledGetAuthCode : InfoEvent
}
