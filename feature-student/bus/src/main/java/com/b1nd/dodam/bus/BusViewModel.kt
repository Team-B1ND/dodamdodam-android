package com.b1nd.dodam.bus

import androidx.lifecycle.ViewModel
import com.b1nd.dodam.bus.model.BusUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(BusUiState())
    val uiState = _uiState.asStateFlow()
}