package com.b1nd.dodam.outing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.utiles.combineWhenAllComplete
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.outing.OutingRepository
import com.b1nd.dodam.outing.model.OutState
import com.b1nd.dodam.outing.model.OutPendingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OutViewModel  : ViewModel(), KoinComponent {

    private val outingRepository: OutingRepository by inject()

    private val _state = MutableStateFlow(OutState())
    val state = _state.asStateFlow()


    fun load()  = viewModelScope.launch {
        val date = DodamDate.localDateNow()

        combineWhenAllComplete(
            outingRepository.getOutings(date),
            outingRepository.getSleepovers(date),
        ) { outing, sleepover ->
            var outPendingCount = 0
            var sleepoverPendingCount = 0

            when (outing) {
                is Result.Success -> {
                    outPendingCount = outing.data.filter { it.status == Status.PENDING }.size
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    outing.error.printStackTrace()
                    return@combineWhenAllComplete OutPendingUiState.Error
                }
            }

            when (sleepover) {
                is Result.Success -> {
                    sleepoverPendingCount = sleepover.data.filter { it.status == Status.PENDING }.size
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    sleepover.error.printStackTrace()
                    return@combineWhenAllComplete OutPendingUiState.Error
                }
            }

            return@combineWhenAllComplete OutPendingUiState.Success(
                outPendingCount = outPendingCount,
                sleepoverPendingCount = sleepoverPendingCount,
            )
        }.collect { state ->
            _state.update {
                it.copy(
                    outPendingUiState = state
                )
            }
        }
    }
}