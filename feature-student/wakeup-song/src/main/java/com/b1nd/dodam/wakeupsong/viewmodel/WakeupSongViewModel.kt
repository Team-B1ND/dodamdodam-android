package com.b1nd.dodam.wakeupsong.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.wakeupsong.WakeupSongRepository
import com.b1nd.dodam.wakeupsong.WakeupSongUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@HiltViewModel
class WakeupSongViewModel @Inject constructor(
) : ViewModel(), KoinComponent {
    private val wakeupSongRepository: WakeupSongRepository by inject()

    private val _uiState = MutableStateFlow(WakeupSongUiState())
    val uiState = _uiState.asStateFlow()
    private val current = LocalDateTime.now()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun getMyWakeupSongs() {
        viewModelScope.launch {
            wakeupSongRepository.getMyWakeupSongs().collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            uiState.copy(
                                isLoading = false,
                                myWakeupSongs = result.data,
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isLoading = true,
                            )
                        }

                        is Result.Error -> {
                            Log.e("WakeupSongViewModel", result.error.stackTraceToString())
                            uiState.copy(
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    fun getAllowedWakeupSongs() {
        viewModelScope.launch {
            wakeupSongRepository.getAllowedWakeupSongs(
                current.year,
                current.monthValue,
                current.dayOfMonth,
            ).collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            uiState.copy(
                                isLoading = false,
                                allowedWakeupSongs = result.data,
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isLoading = true,
                            )
                        }

                        is Result.Error -> {
                            Log.e("WakeupSongViewModel", result.error.stackTraceToString())
                            uiState.copy(
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    fun getPendingWakeupSongs() {
        viewModelScope.launch {
            wakeupSongRepository.getPendingWakeupSongs().collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            uiState.copy(
                                isLoading = false,
                                pendingWakeupSongs = result.data,
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isLoading = true,
                            )
                        }

                        is Result.Error -> {
                            uiState.copy(
                                isLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    fun deleteWakeupSong(id: Long) {
        viewModelScope.launch {
            wakeupSongRepository.deleteWakeupSong(id).collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            _event.emit(Event.DeleteWakeupSong)
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
                            Log.e("WakeupSongViewModel", result.error.stackTraceToString())
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
    data object DeleteWakeupSong : Event
}
