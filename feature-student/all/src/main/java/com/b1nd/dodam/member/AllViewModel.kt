package com.b1nd.dodam.member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AllViewModel : ViewModel(), KoinComponent {

    private val memberRepository: MemberRepository by inject()

    private val _uiState = MutableStateFlow(AllUiState())
    val uiState = _uiState.asStateFlow()

    fun getMyInfoAndCheckDormitory() {
        viewModelScope.launch {
            combine(
                memberRepository.getMyInfo(),
                memberRepository.checkDormitoryMangeStudent(),
            ) { myInfoResult, dormitoryResult ->
                Pair(myInfoResult, dormitoryResult)
            }.collect { (myInfoResult, dormitoryResult) ->
                _uiState.update { currentState ->
                    val newState = when (myInfoResult) {
                        is Result.Loading -> currentState.copy(isLoading = true, isSimmer = true)
                        is Result.Error -> currentState.copy(isLoading = false, isSimmer = false)
                        is Result.Success -> currentState.copy(
                            memberInfo = myInfoResult.data,
                            isLoading = false,
                            isSimmer = false,
                        )
                    }

                    when (dormitoryResult) {
                        is Result.Loading -> newState.copy(isLoading = true, isSimmer = true)
                        is Result.Error -> newState
                        is Result.Success -> newState.copy(isDormitoryManagementStudent = dormitoryResult.data)
                    }
                }
            }
        }
    }
}

sealed interface Event {
    data class Error(val message: String) : Event
}
