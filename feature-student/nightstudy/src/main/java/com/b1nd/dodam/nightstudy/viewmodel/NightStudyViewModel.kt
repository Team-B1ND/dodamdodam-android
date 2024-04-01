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
import kotlinx.coroutines.flow.collect
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

    fun deleteNightStudy(id: Long) = viewModelScope.launch {
        nightStudyRepository.deleteNightStudy(id).collect { result ->
            when (result) {
                is Result.Success -> {
                    getMyNightStudy()
                    _event.emit(Event.ShowToast)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
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
                    _event.emit(Event.Error(result.error.message.toString()))
                }
            }
        }
    }
}

sealed interface Event {
    data class Error(val message: String) : Event
    data object ShowToast : Event
}
