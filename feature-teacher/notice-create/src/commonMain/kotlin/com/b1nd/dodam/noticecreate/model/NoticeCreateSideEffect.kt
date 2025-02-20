package com.b1nd.dodam.noticecreate.model

sealed interface NoticeCreateSideEffect {
    data object SuccessCreate: NoticeCreateSideEffect
    data class FailedCreate(val throwable: Throwable): NoticeCreateSideEffect
}