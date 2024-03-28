package com.b1nd.dodam.student.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.banner.BannerRepository
import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.data.outing.OutingRepository
import com.b1nd.dodam.data.schedule.ScheduleRepository
import com.b1nd.dodam.student.home.model.BannerUiState
import com.b1nd.dodam.student.home.model.HomeUiState
import com.b1nd.dodam.student.home.model.MealUiState
import com.b1nd.dodam.student.home.model.NightStudyUiState
import com.b1nd.dodam.student.home.model.OutUiState
import com.b1nd.dodam.student.home.model.ScheduleUiState
import com.b1nd.dodam.student.home.model.WakeupSongUiState
import com.b1nd.dodam.wakeupsong.WakeupSongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.immutableMapOf
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.plus
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalTime

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val wakeupSongRepository: WakeupSongRepository,
    private val outingRepository: OutingRepository,
    private val nightStudyRepository: NightStudyRepository,
    private val scheduleRepository: ScheduleRepository,
    private val bannerRepository: BannerRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val current = LocalDateTime.now()
    private val mealTime = if (current.toLocalTime() > LocalTime.of(19, 10)) {
        current.plusDays(1)
    } else { current }

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
                                        mealUiState = MealUiState.Success(
                                            persistentMapOf(
                                                "아침" to (result.data.breakfast?.details?.joinToString(separator = ", ") { menu -> menu.name } ?: ""),
                                                "점심" to (result.data.lunch?.details?.joinToString(separator = ", ") { menu -> menu.name } ?: ""),
                                                "저녁" to (result.data.dinner?.details?.joinToString(separator = ", ") { menu -> menu.name } ?: ""),
                                            ).filterValues { meal -> meal.isNotBlank() }.toImmutableMap()
                                        ),
                                    )
                                }

                            is Result.Error -> {
                                Log.e("getMeal", result.error.toString())
                                _uiState.update {
                                    it.copy(
                                        showShimmer = false,
                                        mealUiState = MealUiState.Error
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
                wakeupSongRepository.getAllowedWakeupSongs(
                    current.year,
                    current.monthValue,
                    current.dayOfMonth,
                )
                    .collect { result ->
                        when (result) {
                            is Result.Success -> _uiState.update {
                                it.copy(
                                    showShimmer = false,
                                    wakeupSongUiState = WakeupSongUiState.Success(result.data),
                                )
                            }

                            is Result.Error -> {
                                Log.e("getWakeupSong", result.error.toString())
                                _uiState.update {
                                    it.copy(
                                        showShimmer = false,
                                        wakeupSongUiState = WakeupSongUiState.Error
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
                outingRepository.getMyOut()
                    .collect { result ->
                        when (result) {
                            is Result.Success -> _uiState.update {
                                it.copy(
                                    outUiState = OutUiState.Success(
                                        result.data.minByOrNull { out ->
                                            out.startAt
                                        },
                                    ),
                                    showShimmer = false
                                )
                            }

                            is Result.Error -> {
                                Log.e("getMyOutSleeping", result.error.toString())
                                _uiState.update {
                                    it.copy(
                                        outUiState = OutUiState.Error,
                                        showShimmer = false
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
                nightStudyRepository.getMyNightStudy()
                    .collect { result ->
                        when (result) {
                            is Result.Success -> _uiState.update {
                                it.copy(
                                    nightStudyUiState = NightStudyUiState.Success(
                                        result.data.minByOrNull { nightStudy ->
                                            nightStudy.startAt
                                        },
                                    ),
                                    showShimmer = false
                                )
                            }

                            is Result.Loading -> _uiState.update {
                                it.copy(showShimmer = true)
                            }

                            is Result.Error -> {
                                Log.e("getMyNightStudy", result.error.toString())
                                _uiState.update {
                                    it.copy(
                                        nightStudyUiState = NightStudyUiState.Error,
                                        showShimmer = false
                                    )
                                }
                            }
                        }
                    }
            }
            launch {
                scheduleRepository.getScheduleBetweenPeriods(
                    startDate = LocalDate.of(localDate.year, localDate.monthNumber, localDate.dayOfMonth).toKotlinLocalDate(),
                    endDate = LocalDate.of(nextDate.year, nextDate.monthNumber, nextDate.dayOfMonth).toKotlinLocalDate(),
                ).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _uiState.update {
                                it.copy(
                                    scheduleUiState = ScheduleUiState.Success(result.data),
                                    showShimmer = false
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
                                    showShimmer = false
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
                                mealUiState = MealUiState.Success(
                                    persistentMapOf(
                                        "아침" to (result.data.breakfast?.details?.joinToString(separator = ", ") { menu -> menu.name } ?: ""),
                                        "점심" to (result.data.lunch?.details?.joinToString(separator = ", ") { menu -> menu.name } ?: ""),
                                        "저녁" to (result.data.dinner?.details?.joinToString(separator = ", ") { menu -> menu.name } ?: ""),
                                    ).filterValues { meal -> meal.isNotBlank() }.toImmutableMap()
                                ),
                            )
                        }

                    is Result.Error -> {
                        Log.e("getMeal", result.error.toString())
                        _uiState.update {
                            it.copy(
                                mealUiState = MealUiState.Error
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

    fun fetchWakeupSong() = viewModelScope.launch {
        wakeupSongRepository.getAllowedWakeupSongs(
            current.year,
            current.monthValue,
            current.dayOfMonth,
        )
            .collect { result ->
                when (result) {
                    is Result.Success -> _uiState.update {
                        it.copy(
                            wakeupSongUiState = WakeupSongUiState.Success(result.data),
                        )
                    }

                    is Result.Error -> {
                        Log.e("fetchWakeupSong", result.error.toString())
                        _uiState.update {
                            it.copy(
                                wakeupSongUiState = WakeupSongUiState.Error
                            )
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(
                                wakeupSongUiState = WakeupSongUiState.Loading,
                            )
                        }
                        delay(DELAY_TIME)
                    }
                }
            }
    }

    fun fetchOut() = viewModelScope.launch {
        outingRepository.getMyOut()
            .collect { result ->
                when (result) {
                    is Result.Success -> _uiState.update {
                        it.copy(
                            outUiState = OutUiState.Success(
                                result.data.minByOrNull { out ->
                                    out.startAt
                                },
                            ),
                        )
                    }

                    is Result.Error -> {
                        Log.e("fetchMyOutSleeping", result.error.toString())
                        _uiState.update {
                            it.copy(
                                outUiState = OutUiState.Error
                            )
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(
                                outUiState = OutUiState.Loading,
                            )
                        }
                        delay(DELAY_TIME)
                    }
                }
            }
    }

    fun fetchNightStudy() = viewModelScope.launch {
        nightStudyRepository.getMyNightStudy()
            .collect { result ->
                when (result) {
                    is Result.Success -> _uiState.update {
                        it.copy(
                            nightStudyUiState = NightStudyUiState.Success(
                                result.data.minByOrNull { nightStudy ->
                                    nightStudy.startAt
                                },
                            ),
                        )
                    }

                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(
                                nightStudyUiState = NightStudyUiState.Loading,
                            )
                        }
                        delay(DELAY_TIME)
                    }

                    is Result.Error -> {
                        Log.e("fetchMyNightStudy", result.error.toString())
                        _uiState.update {
                            it.copy(
                                nightStudyUiState = NightStudyUiState.Error
                            )
                        }
                    }
                }
            }
    }

    fun fetchSchedule() = viewModelScope.launch {
        scheduleRepository.getScheduleBetweenPeriods(
            startDate = LocalDate.of(localDate.year, localDate.monthNumber, localDate.dayOfMonth).toKotlinLocalDate(),
            endDate = LocalDate.of(nextDate.year, nextDate.monthNumber, nextDate.dayOfMonth).toKotlinLocalDate(),
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
