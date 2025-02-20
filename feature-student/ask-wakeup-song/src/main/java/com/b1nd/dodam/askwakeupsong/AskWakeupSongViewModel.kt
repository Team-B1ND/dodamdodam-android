package com.b1nd.dodam.askwakeupsong

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.askwakeupsong.model.AskWakeupSongUiState
import com.b1nd.dodam.common.exception.LockedException
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.wakeupsong.WakeupSongRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AskWakeupSongViewModel : ViewModel(), KoinComponent {
    private val wakeupSongRepository: WakeupSongRepository by inject()

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
                                isError = false,
                                isLoading = false,
                                melonChartSongs = result.data,
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isError = false,
                                isLoading = true,
                            )
                        }

                        is Result.Error -> {
                            uiState.copy(
                                isError = true,
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
                                isError = false,
                                isSearchLoading = false,
                                searchWakeupSongs = result.data,
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isError = false,
                                isSearchLoading = true,
                            )
                        }

                        is Result.Error -> {
                            _event.emit(Event.ShowToast("기상송 검색에 실패했습니다."))
                            uiState.copy(
                                isError = true,
                                isSearchLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }

    fun postWakeupSongFromYoutubeUrl(url: String) {
        viewModelScope.launch {
            wakeupSongRepository.postWakeupSongFromYoutubeUrl(
                url = url,
            ).collect { result ->

                when (result) {
                    is Result.Success -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                isError = false,
                                isLoading = false,
                            )
                        }
                        _event.emit(Event.ShowToast("기상송 신청에 성공했습니다."))
                        _event.emit(Event.PopBackStack)
                    }

                    is Result.Loading -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                isError = false,
                                isLoading = true,
                            )
                        }
                    }

                    is Result.Error -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                isError = true,
                                isLoading = false,
                            )
                        }
                        when (result.error) {
                            is LockedException -> {
                                _event.emit(Event.ShowToast("이번주에 이미 기상송을 신청했습니다."))
                            }

                            else -> {
                                _event.emit(Event.ShowToast("기상송 신청에 실패했습니다."))
                            }
                        }
                    }
                }
            }
        }
    }

    fun postWakeupSong(artist: String, title: String) {
        viewModelScope.launch {
            wakeupSongRepository.postWakeupSong(
                artist = artist,
                title = title,
            ).collect { result ->

                when (result) {
                    is Result.Success -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                isError = false,
                                isLoading = false,
                            )
                        }
                        _event.emit(Event.ShowToast("기상송 신청에 성공했습니다."))
                        _event.emit(Event.PopBackStack)
                    }

                    is Result.Loading -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                isError = false,
                                isLoading = true,
                            )
                        }
                    }

                    is Result.Error -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                isError = true,
                                isLoading = false,
                            )
                        }
                        when (result.error) {
                            is LockedException -> {
                                _event.emit(Event.ShowToast("이번주에 이미 기상송을 신청했습니다."))
                            }

                            else -> {
                                _event.emit(Event.ShowToast("기상송 신청에 실패했습니다."))
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed interface Event {
    data class ShowToast(val message: String) : Event
    data object PopBackStack : Event
}
