package com.b1nd.dodam.busapply.model

import com.b1nd.dodam.bus.model.Bus
import kotlinx.collections.immutable.persistentListOf

data class BusApplyUiState(
    val isLoading: Boolean = false,
    val buses: List<Bus> = persistentListOf(),
    val selectedBus: Bus? = null,
    val isError: Boolean = false,
)
