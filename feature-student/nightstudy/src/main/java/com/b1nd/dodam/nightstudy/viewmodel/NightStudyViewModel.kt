package com.b1nd.dodam.nightstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NightStudyViewModel: ViewModel(), KoinComponent {
    private val nightStudyRepository: NightStudyRepository by inject()

    private val _uiState: MutableStateFlow<NightStudyUiState> = MutableStateFlow(NightStudyUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getMyNigthStudy()
    }

    fun getMyNigthStudy() {
        viewModelScope.launch {
            nightStudyRepository.getMyNightStudy().collect { result ->
                when (result) {
                    is Result.Success -> _uiState.emit(
                        NightStudyUiState.Success(
                            result.data,
                        ),
                    )

                    is Result.Error -> _uiState.emit(NightStudyUiState.Error)

                    Result.Loading -> _uiState.emit(NightStudyUiState.Loading)
                }
            }
        }
    }

    fun deleteNightStudy(id: Long) = viewModelScope.launch {
        nightStudyRepository.deleteNightStudy(id).collect { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.emit(NightStudyUiState.SuccessDelete)
                    getMyNigthStudy()
                }

                is Result.Loading -> _uiState.emit(NightStudyUiState.Loading)

                is Result.Error -> _uiState.emit(NightStudyUiState.FailDelete)
            }
        }
    }
}

sealed interface NightStudyUiState {
    data class Success(
        val nightStudies: ImmutableList<NightStudy> = persistentListOf(),
    ) : NightStudyUiState

    data object Loading : NightStudyUiState

    data object Error : NightStudyUiState

    data object SuccessDelete : NightStudyUiState

    data object FailDelete : NightStudyUiState
}
