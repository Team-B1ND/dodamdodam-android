package com.b1nd.dodam.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.member.MemberRepository
import com.b1nd.dodam.setting.model.SettingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            memberRepository.getMyInfo().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                name = result.data.name,
                                profile = result.data.profileImage,
                            )
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }

                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }
}