package com.b1nd.dodam.meal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.data.meal.model.Meal
import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.toKotlinLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MealViewModel : ViewModel(), KoinComponent {
    private val current = LocalDateTime.now().toKotlinLocalDateTime()
    private var getOnlyOnce = true

    private var meals: PersistentList<Meal> = persistentListOf()
    private val mealRepository: MealRepository by inject()

    init {
        getMealOfMonth(current.year, current.monthNumber)
        getOnlyOnce = true
    }

    private val _mealUiState: MutableStateFlow<MealUiState> = MutableStateFlow(MealUiState.Loading)
    val mealUiState: StateFlow<MealUiState> = _mealUiState.asStateFlow()

    fun getMealOfMonth(year: Int, month: Int) {
        if (getOnlyOnce) {
            getOnlyOnce = false
            mealRepository.getMealOfMonth(year, month)
                .onEach { result ->
                    when (result) {
                        is Result.Success -> _mealUiState.emit(
                            MealUiState.Success(
                                meals.addAll(
                                    result.data.filter {
                                        it.date >= current.date
                                    },
                                ).also {
                                    meals = it
                                },
                            ),
                        )

                        is Result.Loading -> _mealUiState.emit(MealUiState.Loading)
                        is Result.Error -> _mealUiState.emit(MealUiState.Error)
                    }
                }.launchIn(viewModelScope)
        }
    }
}

sealed interface MealUiState {
    data class Success(val meals: ImmutableList<Meal>) : MealUiState
    data object Error : MealUiState
    data object Loading : MealUiState
}
