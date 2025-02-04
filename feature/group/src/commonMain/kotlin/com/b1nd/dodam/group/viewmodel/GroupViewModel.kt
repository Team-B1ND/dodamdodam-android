package com.b1nd.dodam.group.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.utiles.combineWhenAllComplete
import com.b1nd.dodam.data.division.DivisionRepository
import com.b1nd.dodam.data.division.model.DivisionOverview
import com.b1nd.dodam.group.model.GroupSideEffect
import com.b1nd.dodam.group.model.GroupUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GroupViewModel : ViewModel(), KoinComponent {

    private val divisionRepository: DivisionRepository by inject()

    private val _uiState = MutableStateFlow(GroupUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<GroupSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun loadAllGroupNext() {
        viewModelScope.launch {
            if (uiState.value.isAllLoading) {
                return@launch
            }
            _uiState.update {
                it.copy(
                    isAllLoading = true
                )
            }
            divisionRepository.getAllDivisions(
                lastId = _uiState.value.allGroupLastId,
                limit = PAGE_SIZE
            ).collect {
                when (it) {
                    is Result.Success -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                allGroups = uiState.allGroups.toMutableList().apply {
                                    addAll(it.data)
                                }.toImmutableList(),
                                allGroupLastId = it.data.lastOrNull()?.id?: uiState.allGroups.lastOrNull()?.id ?: 0,
                                isAllLoading = false
                            )
                        }
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        it.error.printStackTrace()
                        _sideEffect.emit(GroupSideEffect.FailedLoad)
                        _uiState.update {
                            it.copy(
                                isAllLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun loadMyGroupNext() {
        viewModelScope.launch {
            if (uiState.value.isMyLoading) {
                return@launch
            }
            _uiState.update {
                it.copy(
                    isMyLoading = true
                )
            }
            divisionRepository.getMyDivisions(
                lastId = _uiState.value.myGroupLastId,
                limit = PAGE_SIZE
            ).collect {
                when (it) {
                    is Result.Success -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                myGroups = uiState.myGroups.toMutableList().apply {
                                    addAll(it.data)
                                }.toImmutableList(),
                                myGroupLastId = it.data.lastOrNull()?.id?: uiState.myGroups.lastOrNull()?.id ?: 0,
                                isMyLoading = false
                            )
                        }
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        it.error.printStackTrace()
                        _sideEffect.emit(GroupSideEffect.FailedLoad)
                        _uiState.update {
                            it.copy(
                                isMyLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


    companion object {
        const val PAGE_SIZE = 20
    }
}