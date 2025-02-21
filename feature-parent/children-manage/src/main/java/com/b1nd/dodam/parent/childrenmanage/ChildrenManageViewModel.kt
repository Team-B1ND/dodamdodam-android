package com.b1nd.dodam.parent.childrenmanage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.member.MemberRepository
import com.b1nd.dodam.parent.childrenmanage.model.ChildrenModel
import com.b1nd.dodam.parent.childrenmanage.model.ChildrenSideEffect
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChildrenManageViewModel : ViewModel(), KoinComponent {
    private val memberRepository: MemberRepository by inject()

    private val _uiState = MutableStateFlow(persistentListOf<ChildrenModel>())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ChildrenSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun getChildren(code: String) {
        viewModelScope.launch {
            memberRepository.getChildren(code = code).collect {
                when (it) {
                    is Result.Success -> {
                        val newChild = ChildrenModel(
                            childrenName = it.data.name,
                            relation = it.data.email,
                        )
                        _uiState.update { it.add(newChild) }
                        _sideEffect.emit(ChildrenSideEffect.SuccessGetChildren)
                    }

                    is Result.Error -> {
                        _sideEffect.emit(ChildrenSideEffect.Failed(it.error ?: Throwable()))
                        it.error.printStackTrace()
                    }

                    is Result.Loading -> {}
                }
            }
        }
    }
}
