package com.b1nd.dodam.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.all.model.AllUiState
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.member.MemberRepository
import com.b1nd.dodam.member.model.ActiveStatus
import com.b1nd.dodam.member.model.MemberInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class AllViewModel: ViewModel(), KoinComponent {

    private val memberRepository: MemberRepository by inject()
    private val dispatcher: CoroutineDispatcher by inject(named(DispatcherType.IO))

    private val _state = MutableStateFlow(AllUiState())
    val state = _state.asStateFlow()

    fun loadProfile() = viewModelScope.launch(dispatcher) {
        memberRepository.getMyInfo().collect {
            when (it) {
                is Result.Success -> {
                    _state.update { state ->
                        state.copy(
                            memberInfo = it.data,
                            isLoading = false
                        )
                    }
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    it.error.printStackTrace()
                    _state.update { state ->
                        state.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}