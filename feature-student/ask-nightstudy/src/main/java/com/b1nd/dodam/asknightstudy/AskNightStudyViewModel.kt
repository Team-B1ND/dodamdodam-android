package com.b1nd.dodam.asknightstudy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.asknightstudy.model.AskNightStudyUiState
import com.b1nd.dodam.common.exception.BadRequestException
import com.b1nd.dodam.common.exception.ConflictException
import com.b1nd.dodam.common.exception.ForbiddenException
import com.b1nd.dodam.common.exception.NotFoundException
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.core.model.Place
import com.b1nd.dodam.data.core.model.ProjectPlace
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AskNightStudyViewModel : ViewModel(), KoinComponent {

    private val nightStudyRepository: NightStudyRepository by inject()

    private val _uiState = MutableStateFlow(AskNightStudyUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun askNightStudy(place: Place, content: String, doNeedPhone: Boolean, reasonForPhone: String?, startAt: LocalDate, endAt: LocalDate) =
        viewModelScope.launch {
            nightStudyRepository.askNightStudy(
                place,
                content,
                doNeedPhone,
                reasonForPhone,
                startAt,
                endAt,
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _event.emit(Event.Success)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }

                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                message = result.error.message.toString(),
                            )
                        }

                        when (result.error) {
                            is ForbiddenException, is NotFoundException, is BadRequestException, is ConflictException -> {
                                _event.emit(Event.ShowDialog)
                            }
                        }
                    }
                }
            }
        }

    fun askProjectNightStudy(type: String, name: String, description: String, startAt: LocalDate, endAt: LocalDate, room: ProjectPlace, students: List<Int>) =
        viewModelScope.launch {
            nightStudyRepository.askProjectStudy(
                type,
                name,
                description,
                startAt,
                endAt,
                room,
                students,
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _event.emit(Event.Success)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                            )
                        }
                    }

                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                message = result.error.message.toString(),
                            )
                        }

                        when (result.error) {
                            is ForbiddenException, is NotFoundException, is BadRequestException, is ConflictException -> {
                                _event.emit(Event.ShowDialog)
                            }
                        }
                    }
                }
            }
        }

    fun getNightStudyStudent() = viewModelScope.launch {
        nightStudyRepository.getNightStudyStudent().collect { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            students = result.data,
                        )
                    }
                }

                is Result.Loading -> {
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            message = result.error.message.toString(),
                        )
                    }

                    when (result.error) {
                        is ForbiddenException, is NotFoundException, is BadRequestException, is ConflictException -> {
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
