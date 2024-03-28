package com.b1nd.dodam.member

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.member.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllViewModel @Inject constructor(
    private val memberRepository: MemberRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AllUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            memberRepository.getMyInfo().collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            Log.d("AllViewModel", "myInfo ${uiState.myInfo?.name}")
                            uiState.copy(
                                isLoading = false,
                                myInfo = result.data
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isLoading = true
                            )
                        }

                        is Result.Error -> {
                            uiState.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed interface Event {
    data class Error(val message: String) : Event
}
