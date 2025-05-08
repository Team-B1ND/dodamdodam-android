package com.b1nd.dodam.nightstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import com.b1nd.dodam.data.nightstudy.model.Project
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NightStudyViewModel : ViewModel(), KoinComponent {
    private val nightStudyRepository: NightStudyRepository by inject()

    private val _uiState: MutableStateFlow<NightStudyUiState> =
        MutableStateFlow(NightStudyUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _projectUiState: MutableStateFlow<ProjectUiState> =
        MutableStateFlow(ProjectUiState.Loading)
    val projectUiState = _projectUiState.asStateFlow()

    init {
        getMyNightStudy()
        getMyBan()
    }

    fun getMyNightStudy() {
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
                    getMyNightStudy()
                }

                is Result.Loading -> _uiState.emit(NightStudyUiState.Loading)

                is Result.Error -> _uiState.emit(NightStudyUiState.FailDelete)
            }
        }
    }

    fun deleteProject(id: Long) = viewModelScope.launch {
        nightStudyRepository.deleteProject(id).collect { result ->
            when (result) {
                is Result.Success -> {
                    _projectUiState.emit(ProjectUiState.SuccessDelete)
                    getMyNightStudy()
                }

                is Result.Loading -> _projectUiState.emit(ProjectUiState.Loading)

                is Result.Error -> _projectUiState.emit(ProjectUiState.FailDelete)
            }
        }
    }

    fun getMyProject() = viewModelScope.launch {
        nightStudyRepository.getProject().collect { result ->
            when (result) {
                is Result.Success -> _projectUiState.emit(ProjectUiState.Success(result.data))

                is Result.Loading -> _projectUiState.emit(ProjectUiState.Loading)

                is Result.Error -> _projectUiState.emit(ProjectUiState.Error)
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

sealed interface ProjectUiState {
    data class Success(
        val project: ImmutableList<Project> = persistentListOf()
    ) : ProjectUiState

    data object Loading : ProjectUiState

    data object Error : ProjectUiState

    data object SuccessDelete : ProjectUiState

    data object FailDelete: ProjectUiState
}