package com.b1nd.dodam.student.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.data.outing.OutingRepository
import com.b1nd.dodam.student.home.model.HomeUiState
import com.b1nd.dodam.wakeupsong.WakeupSongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val wakeupSongRepository: WakeupSongRepository,
    private val outingRepository: OutingRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val current = LocalDateTime.now()
        val mealTime = if (current.isAfter(
                LocalDateTime.of(
                    current.year,
                    current.month,
                    current.dayOfMonth,
                    19,
                    10
                )
            )
        ) current.plusDays(1) else current

        viewModelScope.launch {
            launch {
                mealRepository.getMeal(mealTime.year, mealTime.monthValue, mealTime.dayOfMonth)
                    .collect { result ->
                        when (result) {
                            is Result.Success ->
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        meal = persistentListOf(
                                            result.data.breakfast,
                                            result.data.lunch,
                                            result.data.dinner,
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

                            is Result.Error -> Log.e(
                                "getWakeupSong",
                                result.exception.message.toString()
                            )

                            is Result.Loading -> _uiState.update {
                                it.copy(
                                    isLoading = true,
                                )
                            }
                        }
                    }
            }
            launch {
                outingRepository.getMyOut()
                    .collect { result ->
                        when (result) {
                            is Result.Success -> _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    out = result.data.sortedBy { out ->
                                        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                        val localDateTime = LocalDateTime.parse(out.startOutDate, pattern)
                                        localDateTime
                                    }.toImmutableList(),
                                )
                            }

                            is Result.Error -> Log.e(
                                "getMyOutSleeping",
                                result.exception.message.toString()
                            )

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
