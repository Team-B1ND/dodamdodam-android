package com.b1nd.dodam.teacher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.datastore.model.User
import com.b1nd.dodam.datastore.repository.DataStoreRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TestViewModel : ViewModel(), KoinComponent {

    private val dataStoreRepository: DataStoreRepository by inject()

    private val _state = dataStoreRepository.user.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = User(),
    )
    val state = _state

    fun deleteUser() = viewModelScope.launch {
        dataStoreRepository.deleteUser()
//        dataStoreRepository.user.collect { user ->
//            _state.update {
//                user
//            }
//            KmLogging.debug("user", user.toString())
//        }
    }

    fun saveUser(id: String, pw: String, token: String) = viewModelScope.launch {
        dataStoreRepository.saveUser(
            id = id,
            pw = pw,
            token = token,
        )
    }
}
