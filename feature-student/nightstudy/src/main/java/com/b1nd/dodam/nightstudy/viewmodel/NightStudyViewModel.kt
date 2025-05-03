package com.b1nd.dodam.nightstudy.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.exception.NotFoundException
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.core.model.Student
import com.b1nd.dodam.data.core.model.StudentImage
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.data.nightstudy.model.MyBan
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NightStudyViewModel : ViewModel(), KoinComponent {
    private val nightStudyRepository: NightStudyRepository by inject()

    private val _uiState: MutableStateFlow<NightStudyUiState> =
        MutableStateFlow(NightStudyUiState.Loading)
    val uiState = _uiState.asStateFlow()

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

    fun getMyBan() = viewModelScope.launch {
        nightStudyRepository.myBan().collect { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.banReason != null) {
                        Log.d("나이트", "getMyBan: ${result.data}")
                        _uiState.emit(NightStudyUiState.IsBanned(result.data))
                    }
                    Log.d("나이트", "getMyBan: ${result.data}")
                }

                is Result.Loading -> _uiState.emit(NightStudyUiState.Loading)

                is Result.Error -> {
                    _uiState.emit(NightStudyUiState.BanError)
                }
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

    data class IsBanned(
        val myBan: MyBan
    ) : NightStudyUiState

    data object BanError : NightStudyUiState
}
