package com.b1nd.dodam.bus

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.bus.model.BusUiState
import com.b1nd.dodam.bus.repository.BusRepository
import com.b1nd.dodam.common.exception.DataNotFoundException
import com.b1nd.dodam.common.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module

class BusViewModel: ViewModel(), KoinComponent {

    private val busRepository: BusRepository by inject()

    private val _uiState = MutableStateFlow(BusUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
        getActiveBuses()
    }

    fun applyBus(id: Int) {
        viewModelScope.launch {
            busRepository.applyBus(id).collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            _event.emit(Event.ShowToast("버스 신청에 성공했어요"))
                            getActiveBuses()
                            uiState.copy(
                                isError = false,
                                isLoading = false,
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isError = false,
                                isLoading = true,
                            )
                        }

                        is Result.Error -> {
                            _event.emit(Event.ShowToast("버스 신청에 실패했어요"))
                            Log.e(
                                "BusViewModel",
                                result.error.stackTraceToString(),
                            )
                            uiState.copy(
                                isLoading = false,
                                isError = true,
                            )
                        }
                    }
                }
            }
        }
    }

    fun getMyBus() {
        viewModelScope.launch {
            busRepository.getMyBus().collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            uiState.copy(
                                isError = false,
                                isLoading = false,
                                selectedBus = result.data,
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isError = false,
                                isLoading = true,
                            )
                        }

                        is Result.Error -> {
                            Log.e(
                                "BusViewModel",
                                result.error.stackTraceToString(),
                            )
                            when (result.error) {
                                is DataNotFoundException -> {
                                    uiState.copy(
                                        selectedBus = null,
                                        isLoading = false,
                                    )
                                }

                                else -> {
                                    uiState.copy(
                                        isLoading = false,
                                        isError = true,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun updateBus(id: Int, selectedIndex: Int) {
        viewModelScope.launch {
            val selectedBus = uiState.value.buses[selectedIndex]
            if (selectedBus.applyCount >= selectedBus.peopleLimit) {
                _uiState.update { uiState ->
                    _event.emit(Event.ShowToast("버스가 만석이에요"))
                    uiState.copy(
                        isError = true,
                    )
                }
                return@launch
            }
            busRepository.updateBus(id).collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            getActiveBuses()
                            _event.emit(Event.ShowToast("버스 수정에 성공했어요"))
                            uiState.copy(
                                isError = false,
                                isLoading = false,
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isError = false,
                                isLoading = true,
                            )
                        }

                        is Result.Error -> {
                            Log.e(
                                "BusViewModel",
                                result.error.stackTraceToString(),
                            )
                            _event.emit(Event.ShowToast("버스 수정에 실패했어요"))
                            uiState.copy(
                                isLoading = false,
                                isError = true,
                            )
                        }
                    }
                }
            }
        }
    }

    fun deleteBus(id: Int) {
        viewModelScope.launch {
            busRepository.deleteBus(id).collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            getMyBus()
                            _event.emit(Event.ShowToast("버스 삭제에 성공했어요"))
                            uiState.copy(
                                isError = false,
                                isLoading = false,
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isError = false,
                                isLoading = true,
                            )
                        }

                        is Result.Error -> {
                            Log.e(
                                "BusViewModel",
                                result.error.stackTraceToString(),
                            )
                            _event.emit(Event.ShowToast("버스 신청을 취소했어요"))
                            uiState.copy(
                                isLoading = false,
                                isError = true,
                            )
                        }
                    }
                }
            }
        }
    }

    fun getActiveBuses() {
        viewModelScope.launch {
            busRepository.getBusList().collect { result ->
                _uiState.update { uiState ->
                    when (result) {
                        is Result.Success -> {
                            if (result.data.isEmpty()) {
                                _event.emit(Event.ShowDialog)
                            }
                            getMyBus()
                            uiState.copy(
                                isError = false,
                                isLoading = false,
                                buses = result.data,
                            )
                        }

                        is Result.Loading -> {
                            uiState.copy(
                                isError = false,
                                isLoading = true,
                            )
                        }

                        is Result.Error -> {
                            Log.e(
                                "BusViewModel",
                                result.error.stackTraceToString(),
                            )
                            uiState.copy(
                                isLoading = false,
                                isError = true,
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed interface Event {
    data class ShowToast(val message: String) : Event
    data object ShowDialog : Event
}
