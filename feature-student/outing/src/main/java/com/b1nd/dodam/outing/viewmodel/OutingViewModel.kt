package com.b1nd.dodam.outing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.outing.OutingRepository
import com.b1nd.dodam.data.outing.model.OutType
import com.b1nd.dodam.outing.OutingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class OutingViewModel @Inject constructor(
    private val outingRepository: OutingRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(OutingUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun getMyOuting() {
        viewModelScope.launch {
            outingRepository.getMyOut().collect { result ->
                _uiState.update {
                    when (result) {
                        is Result.Error -> {
                            _event.emit(Event.Error(result.error.message.toString()))
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
                                outings = result.data.filter { outs ->
                                    outs.outType == OutType.OUTING
                                }.toImmutableList(),
                                sleepovers = result.data.filter { outs ->
                                    outs.outType == OutType.SLEEPOVER
                                }.toImmutableList(),
                            )
                        }
                    }
                }
            }
        }
    }

    fun deleteOuting(id: Long) = viewModelScope.launch {
        outingRepository.deleteOuting(id).collect { result ->
            when (result) {
                is Result.Success -> {
                    getMyOuting()
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

    fun deleteSleepover(id: Long) = viewModelScope.launch {
        outingRepository.deleteSleepover(id).collect { result ->
            when (result) {
                is Result.Success -> {
                    getMyOuting()
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
