package com.b1nd.dodam.nightstudy.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.nightstudy.NightStudyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NightStudyViewModel @Inject constructor(
    private val nightStudyRepository: NightStudyRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(NightStudyUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
        getMyNightStudy()
    }

    fun getMyNightStudy() {
        viewModelScope.launch {
            nightStudyRepository.getMyNightStudy().collect { result ->
                _uiState.update {
                    when (result) {
                        is Result.Error -> {
                            _event.emit(Event.Error(result.error.message.toString()))
                            Log.e("ERROR", result.error.message.toString())
                            it.copy(
                                isLoading = false,
                            )
                        }

                        Result.Loading -> {
                            it.copy(
                                isLoading = true,
                            )
                        }

                        is Result.Success -> {
                            it.copy(
                                isLoading = false,
                                nightStudy = result.data,
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
