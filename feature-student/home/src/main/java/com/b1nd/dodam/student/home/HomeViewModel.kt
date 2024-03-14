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
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.plus
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime

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
    private val mealTime = if (current.isAfter(
            LocalDateTime.of(
                current.year,
                current.month,
                current.dayOfMonth,
                19,
                10,
            ),
        )
    ) {
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
                                        mealUiState = MealUiState.Success(
                                            data = persistentListOf(
                                                result.data.breakfast?.details?.joinToString(", ") { menu -> menu.name },
                                                result.data.lunch?.details?.joinToString(", ") { menu -> menu.name },
                                                result.data.dinner?.details?.joinToString(", ") { menu -> menu.name },
                                            ),
                                        ),
                                    )
                                }

                            is Result.Error -> {
                                Log.e("getMeal", result.exception.message.toString())
                                _uiState.update {
                                    it.copy(
                                        mealUiState = MealUiState.Error(result.exception.message.toString()),
                                    )
                                }
                            }

                            is Result.Loading -> _uiState.update {
                                it.copy(
                                    mealUiState = MealUiState.Shimmer,
                                )
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
                                    wakeupSongUiState = WakeupSongUiState.Success(result.data),
                                )
                            }

                            is Result.Error -> {
                                Log.e("getWakeupSong", result.exception.message.toString())
                                _uiState.update {
                                    it.copy(
                                        wakeupSongUiState = WakeupSongUiState.Error(result.exception.message.toString()),
                                    )
                                }
                            }

                            is Result.Loading -> _uiState.update {
                                it.copy(
                                    wakeupSongUiState = WakeupSongUiState.Shimmer,
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
                                    outUiState = OutUiState.Success(
                                        result.data.minByOrNull { out ->
                                            out.startAt
                                        },
                                    ),
                                )
                            }

                            is Result.Error -> {
                                Log.e("getMyOutSleeping", result.exception.message.toString())
                                _uiState.update {
                                    it.copy(
                                        outUiState = OutUiState.Error(
                                            result.exception.message.toString(),
                                        ),
                                    )
                                }
                            }

                            is Result.Loading -> _uiState.update {
                                it.copy(
                                    outUiState = OutUiState.Shimmer,
                                )
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
                                )
                            }

                            is Result.Loading -> _uiState.update {
                                it.copy(
                                    nightStudyUiState = NightStudyUiState.Shimmer,
                                )
                            }

                            is Result.Error -> {
                                Log.e("getMyNightStudy", result.exception.message.toString())
                                _uiState.update {
                                    it.copy(
                                        nightStudyUiState = NightStudyUiState.Error(
                                            result.exception.message.toString(),
                                        ),
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

                            result.data.filter { result.data.first().endDate <= it.startDate }.forEach {
                                Log.d("TEST", it.endDate.toString() + " : " + it.startDate.toString())
                            }
                            _uiState.update {
                                it.copy(
                                    scheduleUiState = ScheduleUiState.Success(result.data),
                                )
                            }
                        }

                        is Result.Loading -> {
                            _uiState.update {
                                it.copy(scheduleUiState = ScheduleUiState.Shimmer)
                            }
                        }

                        is Result.Error -> {
                            Log.e("getSchedule", result.exception.stackTraceToString())
                            _uiState.update {
                                it.copy(scheduleUiState = ScheduleUiState.Error(result.exception.message.toString()))
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
                                Log.e("getBanner", result.exception.stackTraceToString())
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
                    is Result.Loading -> {
                    }
                    is Result.Error -> {
                        Log.e("fetchBanner", result.exception.stackTraceToString())
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
                                    persistentListOf(
                                        result.data.breakfast?.details?.joinToString(", ") { menu -> menu.name },
                                        result.data.lunch?.details?.joinToString(", ") { menu -> menu.name },
                                        result.data.dinner?.details?.joinToString(", ") { menu -> menu.name },
                                    ),
                                ),
                            )
                        }

                    is Result.Error -> {
                        Log.e("getMeal", result.exception.message.toString())
                        _uiState.update {
                            it.copy(
                                mealUiState = MealUiState.Error(result.exception.message.toString()),
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
                        Log.e("fetchWakeupSong", result.exception.message.toString())
                        _uiState.update {
                            it.copy(
                                wakeupSongUiState = WakeupSongUiState.Error(result.exception.message.toString()),
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
                        Log.e("fetchMyOutSleeping", result.exception.message.toString())
                        _uiState.update {
                            it.copy(
                                outUiState = OutUiState.Error(
                                    result.exception.message.toString(),
                                ),
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
                        Log.e("fetchMyNightStudy", result.exception.message.toString())
                        _uiState.update {
                            it.copy(
                                nightStudyUiState = NightStudyUiState.Error(
                                    result.exception.message.toString(),
                                ),
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
                    Log.e("fetchSchedule", result.exception.stackTraceToString())
                    _uiState.update {
                        it.copy(scheduleUiState = ScheduleUiState.Error(result.exception.message.toString()))
                    }
                }
            }
        }
    }

    companion object {
        const val DELAY_TIME = 1000L
    }
}
