package kr.hs.dgsw.login.viewmodel

import androidx.lifecycle.ViewModel
import com.b1nd.dodam.data.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {
    fun login(id: String, pw: String) {
        loginRepository.login(id, pw)
    }
}
