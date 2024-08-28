package com.b1nd.dodam.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.register.repository.RegisterRepository
import com.b1nd.dodam.register.state.RegisterUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RegisterViewModel : ViewModel(), KoinComponent {

    private val registerRepository: RegisterRepository by inject()

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun register(email: String, grade: Int, id: String, name: String, number: Int, phone: String, pw: String, room: Int) = viewModelScope.launch {
        registerRepository.register(
            email = email,
            grade = grade,
            id = id,
            name = name,
            number = number,
            phone = phone,
            pw = pw,
            room = room,
        ).collect { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    _event.emit(Event.NavigateToMain)
                }

                is Result.Error -> {
                    _event.emit(Event.ShowDialog)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.error.message.toString(),
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
            }
        }
    }
}

sealed interface Event {
    data object NavigateToMain : Event
    data object ShowDialog : Event
}
