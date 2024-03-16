package com.b1nd.dodam.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.exception.ForbiddenException
import com.b1nd.dodam.common.exception.NotFoundException
import com.b1nd.dodam.common.exception.UnauthorizedException
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.login.repository.LoginRepository
import com.b1nd.dodam.datastore.repository.DatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val datastoreRepository: DatastoreRepository,
) : ViewModel() {
    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun login(id: String, pw: String) = viewModelScope.launch {
        loginRepository.login(id, pw).collect { result ->
            when (result) {
                is Result.Success -> {
                    datastoreRepository.saveUser(
                        id = id,
                        pw = pw,
                        token = result.data.accessToken,

                    )
                    _event.emit(Event.NavigateToMain)
                }

                is Result.Error -> {
                    when (result.error) {
                        is ForbiddenException -> {
                            _event.emit(Event.Error("아직 계정이 승인되지 않았어요. 승인을 기다려주세요."))
                        }
                        is NotFoundException -> {
                            _event.emit(Event.Error("아이디를 확인해주세요."))
                        }
                        is UnauthorizedException -> {
                            _event.emit(Event.Error("비밀번호를 확인해주세요."))
                        }
                        else -> {
                            Log.e("login: ", "stauts: ${result.error.javaClass.simpleName}")
                            _event.emit(Event.Error("알 수 없는 오류가 발생했습니다. 잠시만 기다려주세요."))
                        }
                    }
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
