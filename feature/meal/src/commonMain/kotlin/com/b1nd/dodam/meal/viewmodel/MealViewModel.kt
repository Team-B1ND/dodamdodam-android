package com.b1nd.dodam.meal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.meal.model.MealUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MealViewModel : ViewModel(), KoinComponent {

    private var loadOnce: Pair<Int, Int>? = null
    private val mealRepository: MealRepository by inject()

    private val _mealUiState: MutableStateFlow<MealUiState> = MutableStateFlow(MealUiState.Loading)
    val mealUiState: StateFlow<MealUiState> = _mealUiState.asStateFlow()

    fun getMealOfMonth(year: Int, month: Int) {
        if (loadOnce?.first == year && loadOnce?.second == month) {
            return
        }
        loadOnce = year to month
        mealRepository.getMealOfMonth(year, month)
            .onEach { result ->
                when (result) {
                    is Result.Success -> _mealUiState.emit(
                        MealUiState.Success(
                            meals = result.data,
                        ),
                    )

                    is Result.Loading -> _mealUiState.emit(MealUiState.Loading)
                    is Result.Error -> _mealUiState.emit(MealUiState.Error)
                }
            }.launchIn(viewModelScope)
    }
}
