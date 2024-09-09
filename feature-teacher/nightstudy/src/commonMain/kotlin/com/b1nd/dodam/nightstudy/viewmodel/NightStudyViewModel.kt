package com.b1nd.dodam.nightstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.nightstudy.state.NightStudyScreenUiState
import com.b1nd.dodam.nightstudy.state.NightStudyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NightStudyViewModel : ViewModel(), KoinComponent {

    private val nightStudyRepository: NightStudyRepository by inject()

    private val _uiState = MutableStateFlow(NightStudyScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun check() {
        viewModelScope.launch {
            nightStudyRepository.getStudyingNightStudy().collect { result ->
                when (result){
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                nightStudyUiState = NightStudyUiState.Success(
                                    result.data
                                ),
                            )
                        }
                    }
                    is Result.Error -> {}
                    Result.Loading -> {}
                }
            }
        }
    }
}