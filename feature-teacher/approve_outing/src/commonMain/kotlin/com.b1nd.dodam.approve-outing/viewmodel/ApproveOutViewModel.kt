package com.b1nd.dodam.approve

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.utiles.combineWhenAllComplete
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.outing.OutingRepository
import com.b1nd.dodam.data.outing.model.Outing
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ApproveOutViewModel : ViewModel(), KoinComponent {
    private val outingRepository: OutingRepository by inject()

    private val _state = MutableStateFlow(ApproveOutState())
    val state = _state.asStateFlow()


    fun load() = viewModelScope.launch {
        val date = DodamDate.localDateNow()

        combineWhenAllComplete(
            outingRepository.getOutings(date),
            outingRepository.getAllSleepovers(date),
        ) { outing, sleepover ->
            var outMembers: ImmutableList<Outing> = persistentListOf()
            var sleepoverMembers: ImmutableList<Outing> = persistentListOf()

            when (outing) {
                is Result.Success -> {
                    outMembers =
                        outing.data.filter { it.status == Status.PENDING }.toImmutableList()
                }

                is Result.Loading -> {}
                is Result.Error -> {
                    outing.error.printStackTrace()
                    return@combineWhenAllComplete OutPendingUiState.Error
                }
            }

            when (sleepover) {
                is Result.Success -> {
                    sleepoverMembers =
                        sleepover.data.filter { it.status == Status.PENDING }.toImmutableList()
                }

                is Result.Loading -> {}
                is Result.Error -> {
                    sleepover.error.printStackTrace()
                    return@combineWhenAllComplete OutPendingUiState.Error
                }
            }

            return@combineWhenAllComplete OutPendingUiState.Success(
                outMembers = outMembers,
                sleepoverMembers = sleepoverMembers
            )
        }.collect { state ->
            _state.update {
                it.copy(
                    outPendingUiState = state
                )
            }
        }
    }

    fun detailMember(
        name: String,
        start: String,
        end: String,
        reason: String
    ) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    detailMember = DetailMember(
                        name = name,
                        start = start,
                        end = end,
                        reason = reason
                    )
                )
            }
        }
    }
}