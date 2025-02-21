package com.b1nd.dodam.parent.children_manage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.member.MemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChildrenManageViewModel: ViewModel(), KoinComponent  {
    private val memberRepository: MemberRepository by inject()

    private val _uiState = MutableStateFlow("")
    val uiState = _uiState.asStateFlow()

    fun getChildren(code: String){
        viewModelScope.launch {
            memberRepository.getChildren(code = code).collect{
                when(it){
                    is Result.Success -> {
                        Log.d("TAG", "getChildren:${it.data} ")
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