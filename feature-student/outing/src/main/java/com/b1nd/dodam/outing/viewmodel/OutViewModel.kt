package com.b1nd.dodam.outing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.outing.OutingRepository
import com.b1nd.dodam.data.outing.model.OutType
import com.b1nd.dodam.data.outing.model.Outing
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@HiltViewModel
class OutingViewModel @Inject constructor(
) : ViewModel(), KoinComponent {

    private val outingRepository: OutingRepository by inject()

    private val _uiState: MutableStateFlow<OutUiState> = MutableStateFlow(OutUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getMyOuting()
    }

    fun getMyOuting() {
        viewModelScope.launch {
            outingRepository.getMyOut().collect { result ->
                when (result) {
                    is Result.Success -> _uiState.emit(
                        OutUiState.Success(
                            outings = result.data.filter { it.outType == OutType.OUTING }
                                .toImmutableList(),
                            sleepovers = result.data.filter { it.outType == OutType.SLEEPOVER }
                                .toImmutableList(),
                        ),
                    )

                    is Result.Error -> _uiState.emit(OutUiState.Error)

                    Result.Loading -> _uiState.emit(OutUiState.Loading)
                }
            }
        }
    }

    fun deleteOuting(id: Long) = viewModelScope.launch {
        outingRepository.deleteOuting(id).collect { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.emit(OutUiState.SuccessDelete)
                    getMyOuting()
                }

                is Result.Loading -> _uiState.emit(OutUiState.Loading)

                is Result.Error -> _uiState.emit(OutUiState.FailDelete)
            }
        }
    }

    fun deleteSleepover(id: Long) = viewModelScope.launch {
        outingRepository.deleteSleepover(id).collect { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.emit(OutUiState.SuccessDelete)
                    getMyOuting()
                }

                is Result.Loading -> _uiState.emit(OutUiState.Loading)

                is Result.Error -> _uiState.emit(OutUiState.FailDelete)
            }
        }
    }
}

sealed interface OutUiState {
    data class Success(
        val outings: ImmutableList<Outing> = persistentListOf(),
        val sleepovers: ImmutableList<Outing> = persistentListOf(),
    ) : OutUiState

    data object Loading : OutUiState

    data object Error : OutUiState

    data object SuccessDelete : OutUiState

    data object FailDelete : OutUiState
}
