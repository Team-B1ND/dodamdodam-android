package com.b1nd.dodam.askout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.askout.model.AskOutUiState
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.outing.OutingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@HiltViewModel
class AskOutViewModel @Inject constructor(
) : ViewModel(), KoinComponent {

    private val outingRepository: OutingRepository by inject()

    private val _uiState = MutableStateFlow(AskOutUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun askOuting(reason: String, startAt: LocalDateTime, endAt: LocalDateTime) = viewModelScope.launch {
        outingRepository.askOuting(reason, startAt, endAt)
            .collect { result ->
                when (result) {
                    is Result.Success -> {
                        _event.emit(Event.Success)
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
                                message = result.error.message.toString(),
                            )
                        }
                    }
                }
            }
    }

    fun askSleepover(reason: String, startAt: LocalDate, endAt: LocalDate) = viewModelScope.launch {
        outingRepository.askSleepover(reason, startAt, endAt)
            .collect { result ->
                when (result) {
                    is Result.Success -> {
                        _event.emit(Event.Success)
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
                                isLoading = true,
                                message = result.error.message.toString(),
                            )
                        }
                    }
                }
            }
    }
}

sealed interface Event {
    data object Success : Event
    data object ShowDialog : Event
}
