package com.b1nd.dodam.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.home.model.HomeUiState
import com.b1nd.dodam.home.model.MealUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel: ViewModel(), KoinComponent {

    private val mealRepository: MealRepository by inject()

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val date = DodamDate.localDateNow()
            loadMeal(date)
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
}