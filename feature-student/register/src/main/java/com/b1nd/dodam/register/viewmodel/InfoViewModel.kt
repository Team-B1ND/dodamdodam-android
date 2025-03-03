package com.b1nd.dodam.register.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.member.MemberRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.register.state.InfoSideEffect
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class InfoViewModel: ViewModel(), KoinComponent {
    private val memberRepository: MemberRepository by inject()

    private val _sideEffect = MutableSharedFlow<InfoSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun getAuthCode(type: String, identifier: String){
        Log.d("TAG", "getAuthCode: $identifier")
        viewModelScope.launch {
            memberRepository.getAuthCode(type, identifier).collect{
                when(it){
                    is Result.Success -> {
                        Log.d("TAG", "성공: ${it.data}")
                        if (type == "PHONE"){
                            _sideEffect.emit(InfoSideEffect.SuccessGetAuthPhoneCode)
                        }else if(type == "EMAIL"){
                            _sideEffect.emit(InfoSideEffect.SuccessGetAuthEmailCode)
                        }
                    }
                    is Result.Error -> {
                        it.error.printStackTrace()
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }
    fun verifyAuthCode(type: String, identifier: String, authCode: String, userAgent: String){
        viewModelScope.launch {
            memberRepository.verifyAuthCode(type, identifier, authCode, userAgent).collect{
                when(it){
                    is Result.Success -> {
                        Log.d("TAG", "verifyAuthCode: ${it.data}")
                    }
                    is Result.Error -> {
                        it.error.printStackTrace()
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }
}