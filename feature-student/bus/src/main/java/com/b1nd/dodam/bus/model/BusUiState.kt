package com.b1nd.dodam.bus.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BusUiState(
    val isLoading: Boolean = false,
    val buses: ImmutableList<Bus> = persistentListOf(),
    val selectedBus: Bus? = null,
    val isError: Boolean = false,
)
