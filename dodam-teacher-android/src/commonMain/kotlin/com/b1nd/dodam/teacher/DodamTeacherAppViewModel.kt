package com.b1nd.dodam.teacher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.datastore.repository.DataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DodamTeacherAppViewModel: ViewModel(), KoinComponent {

    private val dataStoreRepository: DataStoreRepository by inject()

    private val _isLoginState = MutableStateFlow<Boolean?>(null)
    val isLoginState = _isLoginState.asStateFlow()

    fun loadToken() = viewModelScope.launch {
        _isLoginState.value = dataStoreRepository.token.first().isNotEmpty()
    }
}