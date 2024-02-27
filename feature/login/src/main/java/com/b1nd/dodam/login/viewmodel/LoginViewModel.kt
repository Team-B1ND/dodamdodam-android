package com.b1nd.dodam.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.encryptSHA512
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.login.repository.LoginRepository
import com.b1nd.dodam.datastore.repository.DatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val datastoreRepository: DatastoreRepository,
) : ViewModel() {
    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()
    fun login(id: String, pw: String) = viewModelScope.launch {
        loginRepository.login(id, encryptSHA512(pw)).collect { token ->
            when (token) {
                is Result.Success -> {
                    datastoreRepository.saveUser(
                        id = id,
                        pw = pw,
                        token = token.data.accessToken,

                    )
                    _event.emit(Event.NavigateToMain)
                }

                is Result.Error -> {
                    _event.emit(Event.Error(token.exception.message ?: "알 수 없는 오류가 발생했습니다."))
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
