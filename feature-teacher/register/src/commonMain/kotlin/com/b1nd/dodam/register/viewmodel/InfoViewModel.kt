package com.b1nd.dodam.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.member.MemberRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InfoViewModel: ViewModel(), KoinComponent {

    private val memberRepository: MemberRepository by inject()

    private val _event = MutableSharedFlow<InfoEvent>()
    val event = _event.asSharedFlow()

    fun getAuthCode(type: String, identifier: String) {
        viewModelScope.launch {
            memberRepository.getAuthCode(type, identifier).collect {
                when (it) {
                    is Result.Success -> {
                        _event.emit(InfoEvent.SuccessGetAuthPhoneCode)
                    }
                    is Result.Error -> {
                        it.error.printStackTrace()
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }

    fun verifyAuthCode(type: String, identifier: String, authCode: String, userAgent: String) {
        viewModelScope.launch {
            memberRepository.verifyAuthCode(type, identifier, authCode, userAgent).collect {
                when (it) {
                    is Result.Success -> {
                        _event.emit(InfoEvent.SuccessVerifyAuthPhoneCode)
                    }
                    is Result.Error -> {
                        if (it.error.message?.substringBefore(":") == "인증코드가 일치하지 않음") {
                            _event.emit(InfoEvent.FiledVerifyAuthCode)

                        }
                        it.error.printStackTrace()
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }
}

sealed interface InfoEvent {
    data object SuccessGetAuthPhoneCode : InfoEvent
    data object SuccessVerifyAuthPhoneCode : InfoEvent
    data object FiledVerifyAuthCode : InfoEvent
}