package com.b1nd.dodam.approveouting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.approveouting.model.ApproveOutState
import com.b1nd.dodam.approveouting.model.ApproveSideEffect
import com.b1nd.dodam.approveouting.model.DetailMember
import com.b1nd.dodam.approveouting.model.OutPendingUiState
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.utiles.combineWhenAllComplete
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.outing.OutingRepository
import com.b1nd.dodam.data.outing.model.Outing
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ApproveOutViewModel : ViewModel(), KoinComponent {
    private val outingRepository: OutingRepository by inject()

    private val _sideEffect = MutableSharedFlow<ApproveSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

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
                sleepoverMembers = sleepoverMembers,
            )
        }.collect { state ->
            _state.update {
                it.copy(
                    outPendingUiState = state,
                )
            }
        }
    }

    fun allowSleepover(id: Long) {
        viewModelScope.launch {
            outingRepository.allowSleepover(id).collect {
                when (it) {
                    is Result.Error -> {
                        _sideEffect.emit(ApproveSideEffect.Failed(it.error))
                        it.error.printStackTrace()
                    }
                    Result.Loading -> {}
                    is Result.Success -> {
                        filterMember(id, false)
                        _sideEffect.emit(ApproveSideEffect.SuccessApprove)
                    }
                }
            }
        }
    }

    fun allowGoing(id: Long) {
        viewModelScope.launch {
            outingRepository.allowGoing(id).collect {
                when (it) {
                    is Result.Error -> {
                        _sideEffect.emit(ApproveSideEffect.Failed(it.error))
                        it.error.printStackTrace()
                    }
                    Result.Loading -> {}
                    is Result.Success -> {
                        filterMember(id, true)
                        _sideEffect.emit(ApproveSideEffect.SuccessApprove)
                    }
                }
            }
        }
    }

    fun rejectSleepover(id: Long) {
        viewModelScope.launch {
            outingRepository.rejectSleepover(id).collect {
                when (it) {
                    is Result.Error -> {
                        _sideEffect.emit(ApproveSideEffect.Failed(it.error))
                        it.error.printStackTrace()
                    }
                    Result.Loading -> {}
                    is Result.Success -> {
                        filterMember(id, false)
                        _sideEffect.emit(ApproveSideEffect.SuccessReject)
                    }
                }
            }
        }
    }

    fun rejectGoing(id: Long) {
        viewModelScope.launch {
            outingRepository.rejectGoing(id).collect {
                when (it) {
                    is Result.Error -> {
                        _sideEffect.emit(ApproveSideEffect.Failed(it.error))
                        it.error.printStackTrace()
                    }
                    Result.Loading -> {}
                    is Result.Success -> {
                        filterMember(id, true)
                        _sideEffect.emit(ApproveSideEffect.SuccessReject)
                    }
                }
            }
        }
    }

    fun detailMember(name: String, start: String, end: String, reason: String, id: Long) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    detailMember = DetailMember(
                        name = name,
                        start = start,
                        end = end,
                        reason = reason,
                        id = id,
                    ),
                )
            }
        }
    }

    private fun filterMember(id: Long, isOut: Boolean) {
        _state.update {
            if (it.outPendingUiState is OutPendingUiState.Success) {
                if (isOut) {
                    return@update it.copy(
                        outPendingUiState = it.outPendingUiState.copy(
                            outMembers = it.outPendingUiState.outMembers.filter {
                                it.id != id
                            }.toImmutableList(),
                        ),
                    )
                } else {
                    return@update it.copy(
                        outPendingUiState = it.outPendingUiState.copy(
                            sleepoverMembers = it.outPendingUiState.sleepoverMembers.filter {
                                it.id != id
                            }.toImmutableList(),
                        ),
                    )
                }
            }
            it
        }
    }
}
