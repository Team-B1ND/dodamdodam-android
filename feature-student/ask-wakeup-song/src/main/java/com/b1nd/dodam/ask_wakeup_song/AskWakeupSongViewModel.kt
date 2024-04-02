package com.b1nd.dodam.ask_wakeup_song

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.ask_wakeup_song.model.AskWakeupSongUiState
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.wakeupsong.WakeupSongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AskWakeupSongViewModel @Inject constructor(
    private val wakeupSongRepository: WakeupSongRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AskWakeupSongUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
        getMelonChart()
    }

    fun getMelonChart() {
        viewModelScope.launch {
            wakeupSongRepository.getMelonChart().collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            uiState.copy(
                                isLoading = false,
                                melonChartSongs = result.data,
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

    fun searchWakeupSong(keyWord: String) {
        viewModelScope.launch {
            wakeupSongRepository.searchWakeupSong(keyWord).collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            uiState.copy(
                                isLoading = false,
                                searchWakeupSongs = result.data,
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

    fun postWakeupSong(
        artist: String,
        title: String,
    ) {
        viewModelScope.launch {
            wakeupSongRepository.postWakeupSong(
                artist = artist,
                title = title
            ).collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            _event.emit(Event.ShowToast("기상송 신청에 성공했습니다."))
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
                            _event.emit(Event.ShowToast("기상송 신청에 실패했습니다."))
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
    data class ShowToast(val message: String) : Event
}
