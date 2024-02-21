package com.b1nd.dodam.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.encryptSHA512
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()
    fun login(id: String, pw: String) = viewModelScope.launch {
        loginRepository.login(id, encryptSHA512(pw)).collect {
            when (it) {
                is Result.Success -> _event.emit(Event.NavigateToMain)
                is Result.Error -> {
                    Log.e("loginViewModel Error : ", it.exception.message.toString())
                }
                is Result.Loading -> {}
            }
        }
    }
}

sealed interface Event {
    data object NavigateToMain : Event
}
