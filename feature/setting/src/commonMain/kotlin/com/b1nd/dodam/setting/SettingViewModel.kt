package com.b1nd.dodam.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.datastore.repository.DataStoreRepository
import com.b1nd.dodam.member.MemberRepository
import com.b1nd.dodam.setting.model.SettingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingViewModel : ViewModel(), KoinComponent {

    private val memberRepository: MemberRepository by inject()
    private val dataStoreRepository: DataStoreRepository by inject()

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
                                email = result.data.email,
                                phone = result.data.phone
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

    fun deactivate() {
        viewModelScope.launch {
            dataStoreRepository.deleteUser()
            memberRepository.deactivation().collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Error -> {
                            uiState.copy(
                                isLoading = false,
                            )
                        }
                        Result.Loading -> {
                            uiState.copy(
                                isLoading = true,
                            )
                        }
                        is Result.Success -> {
                            uiState.copy(
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreRepository.deleteUser()
        }
    }
}
