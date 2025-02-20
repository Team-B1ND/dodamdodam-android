package com.b1nd.dodam.noticecreate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.division.DivisionRepository
import com.b1nd.dodam.data.division.model.DivisionOverview
import com.b1nd.dodam.data.notice.NoticeRepository
import com.b1nd.dodam.data.notice.model.NoticeFile
import com.b1nd.dodam.data.notice.model.NoticeFileType
import com.b1nd.dodam.data.upload.UploadRepository
import com.b1nd.dodam.noticecreate.model.NoticeCreateSideEffect
import com.b1nd.dodam.noticecreate.model.NoticeCreateUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NoticeCreateViewModel : ViewModel(), KoinComponent {

    private val noticeRepository: NoticeRepository by inject()
    private val uploadRepository: UploadRepository by inject()
    private val divisionRepository: DivisionRepository by inject()

    private val _uiState = MutableStateFlow(NoticeCreateUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<NoticeCreateSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun loadDivisions() {
        viewModelScope.launch {
            divisionRepository.getMyDivisions(
                lastId = 0,
                limit = 9999,
                keyword = "",
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                divisions = result.data.toMutableList().apply {
                                    add(
                                        0,
                                        DivisionOverview(
                                            id = 0,
                                            name = "전체",
                                        ),
                                    )
                                }.toImmutableList(),
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

    fun uploadFile(fileByteArray: ByteArray, fileMimeType: String, fileName: String, noticeFileType: NoticeFileType) {
        viewModelScope.launch {
            _uiState.update { it.copy(isUploadLoading = true) }

            uploadRepository.upload(fileName, fileMimeType, fileByteArray)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            val resolvedType = if (fileMimeType.lowercase() in listOf("jpg", "jpeg", "png", "gif", "bmp", "webp")) {
                                NoticeFileType.IMAGE
                            } else {
                                noticeFileType
                            }

                            val noticeFile = NoticeFile(
                                fileName = "$fileName.$fileMimeType",
                                fileUrl = result.data.profileImage,
                                fileType = resolvedType,
                            )

                            _uiState.update { currentState ->
                                when (resolvedType) {
                                    NoticeFileType.IMAGE -> currentState.copy(
                                        isUploadLoading = false,
                                        images = currentState.images.toMutableList().apply {
                                            add(noticeFile)
                                        }.toImmutableList(),
                                    )

                                    NoticeFileType.FILE -> currentState.copy(
                                        isUploadLoading = false,
                                        files = currentState.files.toMutableList().apply {
                                            add(noticeFile)
                                        }.toImmutableList(),
                                    )
                                }
                            }
                        }
                        Result.Loading -> {}
                        is Result.Error -> {}
                    }
                }
        }
    }

    fun createNotice(title: String, content: String, files: List<NoticeFile>, divisions: List<DivisionOverview>) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isUploadLoading = true,
                )
            }
            noticeRepository.postNoticeCreate(
                title = title,
                content = content,
                files = files,
                divisions = divisions.map { it.id },
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _sideEffect.send(NoticeCreateSideEffect.SuccessCreate)
                        _uiState.update {
                            it.copy(
                                isUploadLoading = false,
                            )
                        }
                    }
                    Result.Loading -> {}
                    is Result.Error -> {
                        _sideEffect.send(NoticeCreateSideEffect.FailedCreate(result.error))
                        _uiState.update {
                            it.copy(
                                isUploadLoading = false,
                            )
                        }
                    }
                }
            }
        }
    }
}
