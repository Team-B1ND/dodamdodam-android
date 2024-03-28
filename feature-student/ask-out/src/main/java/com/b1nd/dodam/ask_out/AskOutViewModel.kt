package com.b1nd.dodam.ask_out

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.outing.OutingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AskOutViewModel @Inject constructor(
    private val outingRepository: OutingRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun askOuting(
        reason: String,
        startAt: LocalDateTime,
        endAt: LocalDateTime,
    ) = viewModelScope.launch {
        outingRepository.askOuting(reason, startAt, endAt)
            .collect { result ->
                when (result) {
                    is Result.Success -> {
                        _event.emit(Event.Success)
                    }

                    is Result.Loading -> {

                    }

                    is Result.Error -> {

                    }
                }
            }
    }

    fun askSleepover(
        reason: String,
        startAt: LocalDate,
        endAt: LocalDate,
    ) = viewModelScope.launch {
        outingRepository.askSleepover(reason, startAt, endAt)
            .collect { result ->
                when (result) {
                    is Result.Success -> {
                        _event.emit(Event.Success)
                    }

                    is Result.Loading -> {

                    }

                    is Result.Error -> {

                    }
                }
            }
    }
}

sealed interface Event {
    data object Success : Event
}
