package com.b1nd.dodam.register.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.register.repository.RegisterRepository
import com.b1nd.dodam.register.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {
    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()
    fun register(
        email: String,
        grade: Int,
        id: String,
        name: String,
        number: Int,
        phone: String,
        pw: String,
        room: Int
    ) = viewModelScope.launch {
        registerRepository.register(
            email = email,
            grade = grade,
            id = id,
            name = name,
            number = number,
            phone = phone,
            pw = pw,
            room = room
        ).collect { result ->
            when (result) {
                is Result.Success -> {
                    _event.emit(Event.NavigateToMain)
                }

                is Result.Error -> {
                    Log.e("Error", result.exception.stackTraceToString())
                    _event.emit(Event.Error(result.exception.message.toString()))
                }

                is Result.Loading -> {}
            }
        }
    }
}

sealed interface Event {
    data object NavigateToMain : Event
    data class Error(val message: String) : Event
}
