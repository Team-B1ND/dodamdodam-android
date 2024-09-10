package com.b1nd.dodam.nightstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.utiles.combineWhenAllComplete
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import com.b1nd.dodam.nightstudy.state.NightStudyScreenUiState
import com.b1nd.dodam.nightstudy.state.NightStudyUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NightStudyViewModel : ViewModel(), KoinComponent {

    private val nightStudyRepository: NightStudyRepository by inject()

    private val _uiState = MutableStateFlow(NightStudyScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            combineWhenAllComplete(
                nightStudyRepository.getStudyingNightStudy(),
                nightStudyRepository.getPendingNightStudy(),
            ) { studying, pending ->
                var studyingMember: ImmutableList<NightStudy> = persistentListOf()
                var pendingMember: ImmutableList<NightStudy> = persistentListOf()

                when (studying) {
                    is Result.Success -> {
                        studyingMember = studying.data
                    }

                    is Result.Error -> {
                        studying.error.printStackTrace()
                        return@combineWhenAllComplete NightStudyUiState.Error
                    }
                    Result.Loading -> {}
                }

                when (pending) {
                    is Result.Success -> {
                        pendingMember = pending.data
                    }

                    is Result.Error -> {
                        pending.error.printStackTrace()
                        return@combineWhenAllComplete NightStudyUiState.Error
                    }
                    Result.Loading -> {}
                }

                return@combineWhenAllComplete NightStudyUiState.Success(
                    pendingData = pendingMember.toImmutableList(),
                    ingData = studyingMember.toImmutableList(),
                )
            }.collect { uiState ->
                _uiState.update {
                    it.copy(
                        nightStudyUiState = uiState,
                    )
                }
            }
        }
    }

    fun allow(id: Long) {
        viewModelScope.launch {
            nightStudyRepository.allowNightStudy(id).collect { result ->
                when (result) {
                    is Result.Success -> {
                    }
                    is Result.Error -> {
                        result.error.printStackTrace()
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
                    }
                    is Result.Error -> {
                        result.error.printStackTrace()
                    }
                    Result.Loading -> {}
                }
            }
        }
    }
}
