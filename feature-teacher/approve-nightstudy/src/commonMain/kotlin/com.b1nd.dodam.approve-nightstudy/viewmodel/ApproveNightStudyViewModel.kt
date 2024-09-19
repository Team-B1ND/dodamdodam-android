package com.b1nd.dodam.approvenightstudy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.utiles.combineWhenAllComplete
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ApproveNightStudyViewModel : ViewModel(), KoinComponent {

    private val nightStudyRepository: NightStudyRepository by inject()


    private val _uiState = MutableStateFlow(NightStudyScreenUiState())
    val uiState = _uiState.asStateFlow()


    fun load() {
        viewModelScope.launch {
            nightStudyRepository.getPendingNightStudy().collect{
                when(it){
                    is Result.Error -> {
                        it.error.printStackTrace()
                    }
                    Result.Loading -> {}
                    is Result.Success -> {
                        _uiState.update { ui ->
                            ui.copy(
                                nightStudyUiState =  NightStudyUiState.Success(
                                    pendingData = it.data
                                )
                            )
                        }
                    }
                }
            }


        }
    }
}