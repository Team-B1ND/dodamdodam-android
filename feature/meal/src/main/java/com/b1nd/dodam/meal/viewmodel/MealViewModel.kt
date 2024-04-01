package com.b1nd.dodam.meal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.meal.MealUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealRepository: MealRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MealUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun getMealOfMonth(year: Int, month: Int) {
        viewModelScope.launch {
            mealRepository.getMealOfMonth(year, month).collect { result ->

                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                showShimmer = false,
                                meal = it.meal.toPersistentList()
                                    .addAll(
//                                        result.data.filter { meal ->
//                                            meal.date.toJavaLocalDate() >= LocalDate.now()
//                                        },
                                        result.data,
                                    ).toImmutableList(),
                            )
                        }
                    }

                    is Result.Error -> {
                        _event.emit(Event.Error(result.error.message.toString()))
                        _uiState.update {
                            it.copy(
                                showShimmer = false,
                            )
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(
                                showShimmer = true,
                            )
                        }
                    }
                }
            }
        }
    }

    fun fetchMealOfMonth(year: Int, month: Int) {
        viewModelScope.launch {
            mealRepository.getMealOfMonth(year, month).collect { result ->
                _uiState.update {
                    when (result) {
                        is Result.Success -> {
                            it.copy(
                                meal = (it.meal + result.data).toImmutableList(),
                                isLoading = false,
                            )
                        }

                        is Result.Error -> {
                            _event.emit(Event.Error(result.error.message.toString()))
                            it.copy(
                                isLoading = false,
                            )
                        }

                        is Result.Loading -> {
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

sealed interface Event {
    data class Error(val message: String) : Event
}
