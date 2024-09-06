package com.b1nd.dodam.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.utiles.combineWhenAllComplete
import com.b1nd.dodam.data.banner.BannerRepository
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.data.outing.OutingRepository
import com.b1nd.dodam.data.schedule.ScheduleRepository
import com.b1nd.dodam.home.model.BannerUiState
import com.b1nd.dodam.home.model.HomeUiState
import com.b1nd.dodam.home.model.MealUiState
import com.b1nd.dodam.home.model.NightStudyUiState
import com.b1nd.dodam.home.model.OutUiState
import com.b1nd.dodam.home.model.ScheduleUiState
import com.b1nd.dodam.logging.KmLogging
import com.b1nd.dodam.logging.logging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.plus
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel: ViewModel(), KoinComponent {

    private val bannerRepository: BannerRepository by inject()
    private val mealRepository: MealRepository by inject()
    private val outingRepository: OutingRepository by inject()
    private val nightStudyRepository: NightStudyRepository by inject()
    private val scheduleRepository: ScheduleRepository by inject()

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            loadBanner()
            loadMeal()
            loadOuting()
            loadNightStudy()
            loadSchedule()
        }
    }

    fun loadBanner() = viewModelScope.launch {
        bannerRepository.getActiveBanner().collect {
            when (it) {
                is Result.Success -> {
                    _state.update { state ->
                        state.copy(
                            showShimmer = false,
                            bannerUiState = BannerUiState.Success(it.data)
                        )
                    }
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    it.error.printStackTrace()
                    _state.update { state ->
                        state.copy(
                            bannerUiState = BannerUiState.None
                        )
                    }
                }

            }
        }
    }

    fun loadMeal() = viewModelScope.launch {
        val date = DodamDate.localDateNow()
        mealRepository.getMeal(
            year = date.year,
            month = date.monthNumber,
            day = date.dayOfMonth
        ).collect {
            when (it) {
                is Result.Success -> {
                    _state.update { state  ->
                        state.copy(
                            showShimmer = false,
                            mealUiState = MealUiState.Success(it.data)
                        )
                    }
                }
                is Result.Loading -> {
                    _state.update { state  ->
                        state.copy(
                            mealUiState = MealUiState.Loading
                        )
                    }
                }
                is Result.Error -> {
                    it.error.printStackTrace()
                    _state.update { state  ->
                        state.copy(
                            mealUiState = MealUiState.Error
                        )
                    }
                }
            }
        }
    }

    fun loadOuting() = viewModelScope.launch {
        val date = DodamDate.localDateNow()
        _state.update {
            it.copy(
                outUiState = OutUiState.Loading
            )
        }
        combineWhenAllComplete(
            outingRepository.getOutings(date),
            outingRepository.getSleepovers(date)
        ) { outing, sleepover ->
            var outAllowCount = 0
            var outPendingCount = 0
            var sleepoverAllowCount = 0
            var sleepoverPendingCount = 0

            when (outing) {
                is Result.Success -> {
                    outAllowCount = outing.data.filter { it.status == Status.ALLOWED }.size
                    outPendingCount = outing.data.filter { it.status == Status.PENDING }.size
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    outing.error.printStackTrace()
                    return@combineWhenAllComplete OutUiState.Error
                }
            }

            when (sleepover) {
                is Result.Success -> {
                    sleepoverAllowCount = sleepover.data.filter { it.status == Status.ALLOWED }.size
                    sleepoverPendingCount = sleepover.data.filter { it.status == Status.PENDING }.size
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    sleepover.error.printStackTrace()
                    return@combineWhenAllComplete OutUiState.Error
                }
            }

            return@combineWhenAllComplete   OutUiState.Success(
                outAllowCount = outAllowCount,
                outPendingCount = outPendingCount,
                sleepoverAllowCount = sleepoverAllowCount,
                sleepoverPendingCount = sleepoverPendingCount
            )
        }.collectLatest {
            _state.update { state ->
                state.copy(
                    showShimmer = false,
                    outUiState = it
                )
            }
        }
    }

    fun loadNightStudy() = viewModelScope.launch {
        _state.update {
            it.copy(
                nightStudyUiState = NightStudyUiState.Loading
            )
        }

        combineWhenAllComplete(
            nightStudyRepository.getNightStudy(),
            nightStudyRepository.getNightStudyPending()
        ){ nightStudyFlow, nightStudyPendingFlow ->
            var activeCount = 0
            var pendingCount = 0

            when (nightStudyFlow) {

                is Result.Success -> {
                    activeCount = nightStudyFlow.data.size
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    nightStudyFlow.error.printStackTrace()
                    return@combineWhenAllComplete NightStudyUiState.Error
                }
            }

            when (nightStudyPendingFlow) {
                is Result.Success -> {
                    pendingCount = nightStudyPendingFlow.data.size
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    nightStudyPendingFlow.error.printStackTrace()
                    return@combineWhenAllComplete NightStudyUiState.Error
                }
            }

            return@combineWhenAllComplete NightStudyUiState.Success(
                active = activeCount,
                pending = pendingCount
            )
        }.collectLatest {
            _state.update { state ->
                state.copy(
                    showShimmer = false,
                    nightStudyUiState = it
                )
            }
        }
    }

    fun loadSchedule() = viewModelScope.launch {
        val startDate = DodamDate.localDateNow()
        val endDate = startDate.plus(DatePeriod(months = 1))
        scheduleRepository.getScheduleBetweenPeriods(
            startAt = startDate,
            endAt = endDate
        ).collect {
            when (it) {
                is Result.Success -> {
                    _state.update {  state ->
                        state.copy(
                            showShimmer = false,
                            scheduleUiState = ScheduleUiState.Success(data = it.data)
                        )
                    }
                }
                is Result.Loading -> {
                    _state.update {
                        it.copy(
                            scheduleUiState = ScheduleUiState.Loading
                        )
                    }
                }
                is Result.Error -> {
                    it.error.printStackTrace()
                    _state.update {  state ->
                        state.copy(
                            showShimmer = false,
                            scheduleUiState = ScheduleUiState.Error
                        )
                    }
                }
            }
        }
    }
}