package com.b1nd.dodam.parnet.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.banner.BannerRepository
import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.data.schedule.ScheduleRepository
import com.b1nd.dodam.student.home.model.BannerUiState
import com.b1nd.dodam.student.home.model.HomeUiState
import com.b1nd.dodam.student.home.model.MealUiState
import com.b1nd.dodam.student.home.model.ScheduleUiState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.plus
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ParentHomeViewModel : ViewModel(), KoinComponent {
    private val mealRepository: MealRepository by inject()
    private val scheduleRepository: ScheduleRepository by inject()
    private val bannerRepository: BannerRepository by inject()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val current = LocalDateTime.now()
    private val mealTime = if (current.toLocalTime() > LocalTime.of(19, 10)) {
        current.plusDays(1)
    } else {
        current
    }

    private val localDate = current.toKotlinLocalDateTime().date
    private val nextDate = localDate.plus(DatePeriod(months = 1))

    init {
        viewModelScope.launch {
            launch {
                mealRepository.getMeal(mealTime.year, mealTime.monthValue, mealTime.dayOfMonth)
                    .collect { result ->
                        when (result) {
                            is Result.Success ->
                                _uiState.update {
                                    it.copy(
                                        showShimmer = false,
                                        mealUiState = MealUiState.Success(result.data),
                                    )
                                }

                            is Result.Error -> {
                                Log.e("getMeal", result.error.toString())
                                _uiState.update {
                                    it.copy(
                                        showShimmer = false,
                                        mealUiState = MealUiState.Error,
                                    )
                                }
                            }

                            is Result.Loading -> _uiState.update {
                                it.copy(showShimmer = true)
                            }
                        }
                    }
            }
            launch {
                scheduleRepository.getScheduleBetweenPeriods(
                    startAt = LocalDate.of(localDate.year, localDate.monthNumber, localDate.dayOfMonth).toKotlinLocalDate(),
                    endAt = LocalDate.of(nextDate.year, nextDate.monthNumber, nextDate.dayOfMonth).toKotlinLocalDate(),
                ).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _uiState.update {
                                it.copy(
                                    scheduleUiState = ScheduleUiState.Success(result.data),
                                    showShimmer = false,
                                )
                            }
                        }

                        is Result.Loading -> {
                            _uiState.update {
                                it.copy(showShimmer = true)
                            }
                        }

                        is Result.Error -> {
                            Log.e("getSchedule", result.error.stackTraceToString())
                            _uiState.update {
                                it.copy(
                                    scheduleUiState = ScheduleUiState.Error,
                                    showShimmer = false,
                                )
                            }
                        }
                    }
                }
            }
            launch {
                bannerRepository.getActiveBanner()
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                _uiState.update {
                                    it.copy(
                                        bannerUiState = BannerUiState.Success(result.data),
                                    )
                                }
                            }
                            is Result.Loading -> {}
                            is Result.Error -> {
                                Log.e("getBanner", result.error.stackTraceToString())
                            }
                        }
                    }
            }
        }
    }

    fun fetchBanner() = viewModelScope.launch {
        bannerRepository.getActiveBanner()
            .collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                bannerUiState = BannerUiState.Success(result.data),
                            )
                        }
                    }
                    is Result.Loading -> {}

                    is Result.Error -> {
                        Log.e("fetchBanner", result.error.stackTraceToString())
                    }
                }
            }
    }

    fun fetchMeal() = viewModelScope.launch {
        mealRepository.getMeal(mealTime.year, mealTime.monthValue, mealTime.dayOfMonth)
            .collect { result ->
                when (result) {
                    is Result.Success ->
                        _uiState.update {
                            it.copy(
                                mealUiState = MealUiState.Success(result.data),
                            )
                        }

                    is Result.Error -> {
                        Log.e("getMeal", result.error.toString())
                        _uiState.update {
                            it.copy(
                                mealUiState = MealUiState.Error,
                            )
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(
                                mealUiState = MealUiState.Loading,
                            )
                        }
                        delay(DELAY_TIME)
                    }
                }
            }
    }

    fun fetchSchedule() = viewModelScope.launch {
        scheduleRepository.getScheduleBetweenPeriods(
            startAt = LocalDate.of(localDate.year, localDate.monthNumber, localDate.dayOfMonth).toKotlinLocalDate(),
            endAt = LocalDate.of(nextDate.year, nextDate.monthNumber, nextDate.dayOfMonth).toKotlinLocalDate(),
        ).collect { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            scheduleUiState = ScheduleUiState.Success(result.data),
                        )
                    }
                }

                is Result.Loading -> {
                    _uiState.update {
                        it.copy(scheduleUiState = ScheduleUiState.Loading)
                    }
                    delay(DELAY_TIME)
                }

                is Result.Error -> {
                    Log.e("fetchSchedule", result.error.stackTraceToString())
                    _uiState.update {
                        it.copy(scheduleUiState = ScheduleUiState.Error)
                    }
                }
            }
        }
    }

    companion object {
        const val DELAY_TIME = 1000L
    }
}
