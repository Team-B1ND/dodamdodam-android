package com.b1nd.dodam.register.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.member.MemberRepository
import com.b1nd.dodam.register.state.InfoSideEffect
import com.b1nd.dodam.register.state.InfoUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class InfoViewModel : ViewModel(), KoinComponent {
    private val memberRepository: MemberRepository by inject()

    private val _sideEffect = Channel<InfoSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _uiState = MutableStateFlow(InfoUiState())
    val uiState = _uiState.asStateFlow()

    fun getAuthCode(type: String, identifier: String) {
        viewModelScope.launch {
            memberRepository.getAuthCode(type, identifier).collect {
                when (it) {
                    is Result.Success -> {
                        if (type == "PHONE") {
                            _sideEffect.send(InfoSideEffect.SuccessGetAuthPhoneCode)
                        } else if (type == "EMAIL") {
                            _sideEffect.send(InfoSideEffect.SuccessGetAuthEmailCode)
                        }
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                    is Result.Error -> {
                        _sideEffect.send(InfoSideEffect.FiledGetAuthCode)
                        it.error.printStackTrace()
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }
        }
    }
    fun verifyAuthCode(type: String, identifier: String, authCode: String, userAgent: String, role: String) {
        viewModelScope.launch {
            memberRepository.verifyAuthCode(type, identifier, authCode, userAgent).collect {
                when (it) {
                    is Result.Success -> {
                        if (type == "PHONE") {
                            if (role == "PARENT") {
                                _sideEffect.send(InfoSideEffect.NavigateToAuth)
                            } else if (role == "STUDENT") {
                                _sideEffect.send(InfoSideEffect.SuccessVerifyAuthPhoneCode)
                            }
                        } else if (type == "EMAIL") {
                            _sideEffect.send(InfoSideEffect.NavigateToAuth)
                        }
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                    is Result.Error -> {
                        if (it.error.message?.substringBefore(":") == "인증코드가 일치하지 않음") {
                            _sideEffect.send(InfoSideEffect.FiledVerifyAuthCode(type))
                        }
                        it.error.printStackTrace()
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }
        }
    }
}
