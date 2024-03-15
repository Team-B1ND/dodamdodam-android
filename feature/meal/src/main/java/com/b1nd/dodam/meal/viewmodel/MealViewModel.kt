package com.b1nd.dodam.meal.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.meal.MealUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDate

@HiltViewModel
class MealViewModel @Inject constructor(
    private val mealRepository: MealRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MealUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    private val current = LocalDate.now()
    val isFirst = MutableLiveData<Boolean>(true)

    init {
        viewModelScope.launch {
            mealRepository.getMealOfMonth(current.year, current.monthValue).collect { result ->
                _uiState.update {
                    when (result) {
                        is Result.Success -> {
                            it.copy(
                                isLoading = false,
                                meal = (
                                    it.meal.toPersistentList()
                                        .addAll(
                                            result.data.filter { meal ->
                                                val date = meal.date.toJavaLocalDate()
                                                date.isAfter(LocalDate.now().minusDays(1))
                                            },
                                        )
                                    ).toImmutableList(),
                            )
                        }

                        is Result.Error -> {
                            _event.emit(Event.Error(result.error.message.toString()))
                            Log.e("ERROR", result.error.message.toString())
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

    fun fetchMealOfMonth(year: Int, month: Int) {
        if (isFirst.value == true) {
            isFirst.value = false
            viewModelScope.launch {
                mealRepository.getMealOfMonth(year, month).collect { result ->
                    _uiState.update {
                        when (result) {
                            is Result.Success -> {
                                it.copy(
                                    meal = (it.meal + result.data).toImmutableList(),
                                    endReached = true,
                                )
                            }

                            is Result.Error -> {
                                _event.emit(Event.Error(result.error.message.toString()))
                                Log.e("ERROR", result.error.message.toString())
                                it.copy(
                                    endReached = true,
                                )
                            }

                            is Result.Loading -> {
                                it.copy(
                                    endReached = true,
                                )
                            }
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
