package com.b1nd.dodam.bus

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.bus.model.BusUiState
import com.b1nd.dodam.bus.repository.BusRepository
import com.b1nd.dodam.common.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(
    private val busRepository: BusRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BusUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
        getActiveBuses()
    }

    fun applyBus(id: Int) {
        viewModelScope.launch {
            busRepository.applyBus(id).collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            _event.emit(Event.APPLY_SUCCESS)
                            uiState.copy(
                                isLoading = false,
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isLoading = true,
                            )
                        }

                        is Result.Error -> {
                            _event.emit(Event.APPLY_ERROR)
                            Log.e(
                                "BusViewModel",
                                result.error.stackTraceToString()
                            )
                            uiState.copy(
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    fun getMyBus() {
        viewModelScope.launch {
            busRepository.getMyBus().collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            uiState.copy(
                                isLoading = false,
                                selectedBus = result.data,
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isLoading = true,
                            )
                        }

                        is Result.Error -> {
                            Log.e(
                                "BusViewModel",
                                result.error.stackTraceToString()
                            )
                            uiState.copy(
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateBus(id: Int) {
        viewModelScope.launch {
            busRepository.updateBus(id).collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            uiState.copy(
                                isLoading = false
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

    fun deleteBus(id: Int) {
        viewModelScope.launch {
            busRepository.deleteBus(id).collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            uiState.copy(
                                isLoading = false
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isLoading = true
                            )
                        }

                        is Result.Error -> {
                            Log.e(
                                "BusViewModel",
                                result.error.stackTraceToString()
                            )
                            uiState.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun getActiveBuses() {
        viewModelScope.launch {
            busRepository.getBusList().collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            uiState.copy(
                                isLoading = false,
                                buses = result.data,
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isLoading = true,
                            )
                        }

                        is Result.Error -> {
                            Log.e(
                                "BusViewModel",
                                result.error.stackTraceToString()
                            )
                            uiState.copy(
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed interface Event {
    data object APPLY_SUCCESS : Event
    data object APPLY_ERROR : Event
}
