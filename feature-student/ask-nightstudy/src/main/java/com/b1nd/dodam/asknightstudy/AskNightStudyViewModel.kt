package com.b1nd.dodam.asknightstudy

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.asknightstudy.model.AskNightStudyUiState
import com.b1nd.dodam.common.exception.BadRequestException
import com.b1nd.dodam.common.exception.ForbiddenException
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.core.model.Place
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AskNightStudyViewModel @Inject constructor(
    private val nightStudyRepository: NightStudyRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AskNightStudyUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun askNightStudy(
        place: Place,
        content: String,
        doNeedPhone: Boolean,
        reasonForPhone: String?,
        startAt: LocalDate,
        endAt: LocalDate,
    ) = viewModelScope.launch {
        nightStudyRepository.askNightStudy(
            place, content, doNeedPhone, reasonForPhone, startAt, endAt
        ).collect { result ->
            when (result) {
                is Result.Success -> {
                    _event.emit(Event.Success)
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }

                is Result.Loading -> {
                    _uiState.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            message = result.error.message.toString()
                        )
                    }
                    when (result.error) {
                        is ForbiddenException -> {
                            _event.emit(Event.ShowDialog)
                        }
                    }
                }
            }
        }
    }
}

sealed interface Event {
    data object Success : Event
    data object ShowDialog : Event
}
