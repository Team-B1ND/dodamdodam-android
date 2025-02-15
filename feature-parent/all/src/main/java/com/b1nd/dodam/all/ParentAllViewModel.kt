package com.b1nd.dodam.all


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.member.MemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ParentAllViewModel : ViewModel(), KoinComponent {

    private val memberRepository: MemberRepository by inject()

    private val _uiState = MutableStateFlow(AllUiState())
    val uiState = _uiState.asStateFlow()

    fun getMyInfo() {
        viewModelScope.launch {
            memberRepository.getMyInfo().collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            uiState.copy(
                                isLoading = false,
                                memberInfo = result.data,
                                isSimmer = false,
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isLoading = true,
                                isSimmer = true,
                            )
                        }

                        is Result.Error -> {
                            uiState.copy(
                                isLoading = false,
                                isSimmer = false,
                            )
                        }
                    }
                }
            }
        }
    }
}
