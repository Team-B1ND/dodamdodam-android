package com.b1nd.dodam.register.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.member.MemberRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.b1nd.dodam.common.result.Result

class InfoViewModel: ViewModel(), KoinComponent {
    private val memberRepository: MemberRepository by inject()

    fun getAuthCode(type: String, identifier: String){
        Log.d("TAG", "getAuthCode: $identifier")
        viewModelScope.launch {
            memberRepository.getAuthCode(type, identifier).collect{
                when(it){
                    is Result.Success -> {
                        Log.d("TAG", "성공: ${it.data}")
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