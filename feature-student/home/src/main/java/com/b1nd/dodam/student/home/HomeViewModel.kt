package com.b1nd.dodam.student.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.student.home.model.HomeUiState
import com.b1nd.dodam.wakeupsong.WakeupSongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val wakeupSongRepository: WakeupSongRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                mealRepository.getMeal(2023, 5, 23)
                    .collect { result ->
                        when (result) {
                            is Result.Success -> _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    meal = Triple(
                                        result.data.breakfast ?: "오늘은 아침이 없어요.",
                                        result.data.lunch ?: "오늘은 점심이 없어요.",
                                        result.data.dinner ?: "오늘은 저녁이 없어요.",
                                    ),
                                )
                            }

                            is Result.Error -> Log.e("getMeal", result.exception.message.toString())

                            is Result.Loading -> _uiState.update {
                                it.copy(
                                    isLoading = true,
                                )
                            }
                        }
                    }
            }
            launch {
                wakeupSongRepository.getAllowedWakeupSongs(2024, 2, 26)
                    .collect { result ->
                        when (result) {
                            is Result.Success -> _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    wakeupSongs = result.data,
                                )
                            }

                            is Result.Error -> Log.e("getWakeupSong", result.exception.message.toString())

                            is Result.Loading -> _uiState.update {
                                it.copy(
                                    isLoading = true,
                                )
                            }
                        }
                    }
            }
        }
    }
}