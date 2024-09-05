package com.b1nd.dodam.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.banner.BannerRepository
import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.home.model.BannerUiState
import com.b1nd.dodam.home.model.HomeUiState
import com.b1nd.dodam.home.model.MealUiState
import com.b1nd.dodam.home.model.NightStudyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel: ViewModel(), KoinComponent {

    private val bannerRepository: BannerRepository by inject()
    private val mealRepository: MealRepository by inject()
    private val nightStudyRepository: NightStudyRepository by inject()

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            loadBanner()
            val date = DodamDate.localDateNow()
            loadMeal(date)
            loadNightStudy()
        }
    }

    private fun loadBanner() = viewModelScope.launch {
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
                    _state.update { state ->
                        state.copy(
                            bannerUiState = BannerUiState.None
                        )
                    }
                }

            }
        }
    }

    fun loadMeal(date: LocalDate) = viewModelScope.launch {
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

    fun loadNightStudy() = viewModelScope.launch {
        val nightStudyFlow = nightStudyRepository.getNightStudy()
        val nightStudyPendingFlow = nightStudyRepository.getNightStudyPending()

        nightStudyFlow.combine(nightStudyPendingFlow){ nightStudyFlow, nightStudyPendingFlow ->
            var activeCount = 0
            var pendingCount = 0

            when (nightStudyFlow) {

                is Result.Success -> {
                    activeCount = nightStudyFlow.data.size
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    return@combine NightStudyUiState.Error
                }
            }

            when (nightStudyPendingFlow) {
                is Result.Success -> {
                    pendingCount = nightStudyPendingFlow.data.size
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    return@combine NightStudyUiState.Error
                }
            }

            return@combine NightStudyUiState.Success(
                active = activeCount,
                pending = pendingCount
            )
        }.collect {
            _state.update { state ->
                state.copy(
                    showShimmer = false,
                    nightStudyUiState = it
                )
            }
        }
    }
}