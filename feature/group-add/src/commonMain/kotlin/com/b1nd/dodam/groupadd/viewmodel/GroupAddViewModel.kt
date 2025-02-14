package com.b1nd.dodam.groupadd.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.division.DivisionRepository
import com.b1nd.dodam.groupadd.model.GroupAddDivisionOverview
import com.b1nd.dodam.groupadd.model.GroupAddSideEffect
import com.b1nd.dodam.groupadd.model.GroupAddUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GroupAddViewModel: ViewModel(), KoinComponent {

    private val divisionRepository: DivisionRepository by inject()

    private val _sideEffect = Channel<GroupAddSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _uiState = MutableStateFlow(GroupAddUiState())
    val uiState = _uiState.asStateFlow()

    fun loadNextGroupList() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            divisionRepository.getAllDivisions(
                lastId = _uiState.value.lastLoadId,
                limit = 20,
                keyword = "",
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                lastLoadId = result.data.lastOrNull()?.id?: it.lastLoadId,
                                divisions =  it.divisions.toMutableList().apply {
                                    addAll(result.data.map {
                                        GroupAddDivisionOverview(
                                            id = it.id,
                                            name = it.name,
                                            isOpen = false,
                                        )
                                    })
                                }.toImmutableList(),
                                isLoading = false,
                            )
                        }
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        result.error.printStackTrace()
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun loadDivisionMembers(divisionId: Int) {
        viewModelScope.launch {
            divisionRepository.getDivisionMembers(
                id = divisionId,
                status = Status.ALLOWED
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                divisionMembers = it.divisionMembers.toMutableMap().apply {
                                    this[divisionId] = result.data
                                }.toImmutableMap()
                            )
                        }
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        result.error.printStackTrace()
                    }
                }
            }
        }
    }

    fun changeOpenState(
        divisionId: Int
    ) {
        _uiState.update {
            it.copy(
                divisions = it.divisions.map {
                    if (it.id != divisionId) {
                        return@map it
                    }
                    return@map it.copy(
                        isOpen = !it.isOpen
                    )
                }.toImmutableList()
            )
        }
    }

    fun addDivisionMember(
        divisionId: Int,
        memberIds: List<String>,
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    addLoading = true
                )
            }
            divisionRepository.postDivisionAddMembers(
                divisionId = divisionId,
                memberId = memberIds
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                addLoading = false
                            )
                        }
                        _sideEffect.send(GroupAddSideEffect.SuccessAddMember)
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                addLoading = false
                            )
                        }
                        _sideEffect.send(GroupAddSideEffect.FailedAddMember)
                    }
                }
            }
        }
    }
}