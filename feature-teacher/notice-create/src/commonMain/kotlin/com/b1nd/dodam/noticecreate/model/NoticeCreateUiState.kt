package com.b1nd.dodam.noticecreate.model

import com.b1nd.dodam.data.division.model.DivisionOverview
import com.b1nd.dodam.data.notice.model.NoticeFile
import com.b1nd.dodam.data.upload.model.UploadModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class NoticeCreateUiState(
    val divisions: ImmutableList<DivisionOverview> = persistentListOf(),
    val isUploadLoading: Boolean = false,
    val images: ImmutableList<NoticeFile> = persistentListOf(),
    val files: ImmutableList<NoticeFile> = persistentListOf(),
)