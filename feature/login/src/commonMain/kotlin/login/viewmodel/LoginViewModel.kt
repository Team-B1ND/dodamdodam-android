package login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.exception.ForbiddenException
import com.b1nd.dodam.common.exception.NotFoundException
import com.b1nd.dodam.common.exception.UnauthorizedException
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.login.repository.LoginRepository
import com.b1nd.dodam.datastore.repository.DataStoreRepository
import login.model.LoginUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel : ViewModel(), KoinComponent {

    private val loginRepository: LoginRepository by inject()
    private val datastoreRepository: DataStoreRepository by inject()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun login(id: String, pw: String) = viewModelScope.launch {
        loginRepository.login(id, pw).collect { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    datastoreRepository.saveUser(
                        id = id,
                        pw = pw,
                        token = result.data.accessToken,

                    )
                    _event.emit(Event.NavigateToMain)
                }

                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    when (result.error) {
                        is ForbiddenException -> {
                            _event.emit(Event.ShowDialog)
                            _uiState.update { it.copy(error = "아직 계정이 승인되지 않았어요") }
                        }
                        is NotFoundException -> {
                            _event.emit(Event.CheckId("아이디를 확인해주세요."))
                        }
                        is UnauthorizedException -> {
                            _event.emit(Event.CheckPw("비밀번호를 확인해주세요."))
                        }
                        else -> {
                            _event.emit(Event.ShowDialog)
                            _uiState.update { it.copy(error = "알 수 없는 오류가 발생했어요") }
                        }
                    }
                }

                is Result.Loading -> {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                }
            }
        }
    }
}

sealed interface Event {
    data object NavigateToMain : Event
    data object ShowDialog : Event
    data class CheckId(val message: String) : Event
    data class CheckPw(val message: String) : Event
}
