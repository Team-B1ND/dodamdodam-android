package com.b1nd.dodam.editmemberinfo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.upload.UploadRepository
import com.b1nd.dodam.editmemberinfo.model.EditMemberInfoSideEffect
import com.b1nd.dodam.editmemberinfo.model.ProfileModel
import com.b1nd.dodam.member.MemberRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EditMemberInfoViewModel : ViewModel(), KoinComponent {
    private val memberRepository: MemberRepository by inject()
    private val uploadRepository: UploadRepository by inject()

    private val _uiState = MutableStateFlow(ProfileModel())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<EditMemberInfoSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun setProfile(
        profileImage: String?
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                image = profileImage
            )
        }
    }

    fun editMember(
        email: String,
        name: String,
        phone: String,
        profileImage: String?
    ) {
        viewModelScope.launch {
            memberRepository.editMemberInfo(
                name = name,
                email = email,
                phone = phone,
                profileImage = profileImage
            ).collect {
                when (it) {
                    is Result.Success -> {
                        _sideEffect.emit(EditMemberInfoSideEffect.SuccessEditMemberInfo)
                        println(it.data)
                    }

                    is Result.Error -> {
                        it.error.printStackTrace()
                    }

                    is Result.Loading -> {}
                }
            }
        }
    }

    fun fileUpload(
        fileByteArray: ByteArray,
        fileMimeType: String,
        fileName: String
    ) {
        viewModelScope.launch {
            uploadRepository.upload(
                fileName = fileName,
                fileMimeType = fileMimeType,
                byteArray = fileByteArray
            ).collect {
                when (it) {
                    is Result.Error -> {
                        it.error.printStackTrace()
                    }

                    is Result.Success -> {
                        _uiState.update { ui ->
                            println(it.data)
                            ui.copy(
                                image = it.data.profileImage
                            )
                        }
                    }

                    is Result.Loading -> {}
                }
            }
        }
    }
}