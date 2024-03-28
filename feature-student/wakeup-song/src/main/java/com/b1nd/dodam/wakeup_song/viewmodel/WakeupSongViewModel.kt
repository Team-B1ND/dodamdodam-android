package com.b1nd.dodam.wakeup_song.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.wakeup_song.WakeupSongUiState
import com.b1nd.dodam.wakeupsong.WakeupSongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class WakeupSongViewModel @Inject constructor(
    private val wakeupSongRepository: WakeupSongRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(WakeupSongUiState())
    val uiState = _uiState.asStateFlow()
    private val current = LocalDateTime.now()

    init {
        getPendingWakeupSongs()
        getMyWakeupSong()
        getAllowedWakeupSongs()
    }

    fun getMyWakeupSong() {
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
                                isLoading = true
                            )
                        }

                        is Result.Error -> {
                            Log.e("WakeupSongViewModel", result.error.stackTraceToString())
                            uiState.copy(
                                isLoading = false
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
                                allowedWakeupSongs = result.data
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isLoading = true
                            )
                        }

                        is Result.Error -> {
                            Log.e("WakeupSongViewModel", result.error.stackTraceToString())
                            uiState.copy(
                                isLoading = false
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
                                pendingWakeupSongs = result.data
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
}
