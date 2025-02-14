package com.b1nd.dodam.groupcreate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.division.DivisionRepository
import com.b1nd.dodam.groupcreate.model.GroupCreateSideEffect
import com.b1nd.dodam.groupcreate.model.GroupCreateUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GroupCreateViewModel: ViewModel(), KoinComponent {

    private val divisionRepository: DivisionRepository by inject()

    private val _uiState = MutableStateFlow(GroupCreateUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<GroupCreateSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun createGroup(
        name: String,
        description: String,
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            divisionRepository.postCreateDivision(
                name = name,
                description = description,
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _sideEffect.send(GroupCreateSideEffect.SuccessGroupCreate)
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        result.error.printStackTrace()
                        _sideEffect.send(GroupCreateSideEffect.FailedGroupCreate(result.error))
                    }
                }
            }
        }
    }
}