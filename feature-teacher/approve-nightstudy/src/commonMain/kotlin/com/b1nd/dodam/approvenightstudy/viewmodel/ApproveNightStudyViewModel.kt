package com.b1nd.dodam.approvenightstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.approvenightstudy.model.DetailMember
import com.b1nd.dodam.approvenightstudy.model.NightStudyScreenUiState
import com.b1nd.dodam.approvenightstudy.model.NightStudySideEffect
import com.b1nd.dodam.approvenightstudy.model.NightStudyUiState
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ApproveNightStudyViewModel : ViewModel(), KoinComponent {

    private val nightStudyRepository: NightStudyRepository by inject()

    private val _sideEffect = MutableSharedFlow<NightStudySideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(NightStudyScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            nightStudyRepository.getPendingNightStudy().collect {
                when (it) {
                    is Result.Error -> {
                        it.error.printStackTrace()
                    }

                    Result.Loading -> {}
                    is Result.Success -> {
                        _uiState.update { ui ->
                            ui.copy(
                                nightStudyUiState = NightStudyUiState.Success(
                                    pendingData = it.data,
                                ),
                            )
                        }
                    }
                }
            }
        }
    }

    fun detailMember(name: String, start: String, end: String, reason: String, id: Long, place: String, doNeedPhone: Boolean, reasonForPhone: String?) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    detailMember = DetailMember(
                        id = id,
                        name = name,
                        startDay = start,
                        endDay = end,
                        place = place,
                        content = reason,
                        doNeedPhone = doNeedPhone,
                        reasonForPhone = reasonForPhone,
                    ),
                )
            }
        }
    }

    fun allow(id: Long) {
        viewModelScope.launch {
            nightStudyRepository.allowNightStudy(id).collect { result ->
                when (result) {
                    is Result.Success -> {
                        filterMember(id)
                        _sideEffect.emit(NightStudySideEffect.SuccessAllow)
                    }
                    is Result.Error -> {
                        result.error.printStackTrace()
                        _sideEffect.emit(NightStudySideEffect.Failed(result.error))
                    }
                    Result.Loading -> {}
                }
            }
        }
    }

    fun reject(id: Long) {
        viewModelScope.launch {
            nightStudyRepository.rejectNightStudy(id).collect { result ->
                when (result) {
                    is Result.Success -> {
                        filterMember(id)
                        _sideEffect.emit(NightStudySideEffect.SuccessReject)
                    }
                    is Result.Error -> {
                        result.error.printStackTrace()
                        _sideEffect.emit(NightStudySideEffect.Failed(result.error))
                    }
                    Result.Loading -> {}
                }
            }
        }
    }

    private fun filterMember(id: Long) {
        _uiState.update {
            if (it.nightStudyUiState is NightStudyUiState.Success) {
                return@update it.copy(
                    nightStudyUiState = it.nightStudyUiState.copy(
                        pendingData = it.nightStudyUiState.pendingData.filter {
                            it.id != id
                        }.toImmutableList(),
                    ),
                )
            }
            it
        }
    }
}
