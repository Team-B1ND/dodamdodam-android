package com.b1nd.dodam.noticecreate.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import coil3.compose.AsyncImage
import com.b1nd.dodam.data.notice.model.NoticeFileType
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.DividerType
import com.b1nd.dodam.designsystem.component.DodamDivider
import com.b1nd.dodam.designsystem.component.DodamTextButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TextButtonSize
import com.b1nd.dodam.designsystem.component.TextButtonType
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.noticecreate.model.NoticeCreateUiState
import com.b1nd.dodam.noticecreate.viewmodel.NoticeCreateViewModel
import com.b1nd.dodam.ui.component.modifier.`if`
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NoticeCreateFirstScreen(
    viewModel: NoticeCreateViewModel,
    uiState: NoticeCreateUiState,
    title: String,
    onTitleValueChange: (String) -> Unit,
    content: String,
    onBodyValueChange: (String) -> Unit,
    popBackStack: () -> Unit,
    onNextClick: () -> Unit,
) {
    val context = LocalPlatformContext.current
    val coroutineScope = rememberCoroutineScope()

    var isFileOpenMode by remember { mutableStateOf(false) }
    var selectTab: NoticeFileType by remember { mutableStateOf(NoticeFileType.IMAGE) }

    val imagePickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            coroutineScope.launch {
                files.firstOrNull()?.let { file ->
                    val fileName = file.getName(context)?.substringBeforeLast('.') ?: "unknown"
                    val fileMimeType = file.getName(context)?.substringAfterLast('.', "") ?: ""

                    viewModel.uploadFile(
                        fileByteArray = file.readByteArray(context),
                        fileMimeType = fileMimeType,
                        fileName = fileName,
                        noticeFileType = NoticeFileType.IMAGE
                    )
                }
            }
        },
    )

    val filePickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.All,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            coroutineScope.launch {
                files.firstOrNull()?.let { file ->
                    val fileName = file.getName(context)?.substringBeforeLast('.') ?: "unknown"
                    val fileMimeType = file.getName(context)?.substringAfterLast('.', "") ?: ""
                    viewModel.uploadFile(
                        fileByteArray = file.readByteArray(context),
                        fileMimeType = fileMimeType,
                        fileName = fileName,
                        noticeFileType = NoticeFileType.FILE
                    )
                }
            }
        },
    )

    val bodyFocusRequester = remember { FocusRequester() }

    Box(
        Modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                DodamTopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    title = "공지 작성",
                    onBackClick = popBackStack,
                )
            },
            containerColor = DodamTheme.colors.backgroundNormal,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 16.dp,
                    )
                    .padding(it),
            ) {
                Spacer(Modifier.height(8.dp))
                DodamTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    label = "제목을 입력하세요",
                    onValueChange = {
                        onTitleValueChange(it)
                    },
                    onClickRemoveRequest = {
                        onTitleValueChange("")
                    },
                )
                Spacer(Modifier.height(24.dp))
                DodamTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    bodyFocusRequester.requestFocus()
                                },
                            )
                        },
                    value = content,
                    label = "대소고에 새로운 소식을 전해보세요",
                    onValueChange = {
                        onBodyValueChange(it)
                    },
                    onClickRemoveRequest = {
                        onBodyValueChange("")
                    },
                    isShowDivider = false,
                    focusRequester = bodyFocusRequester,
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .`if`(isFileOpenMode) {
                            weight(4f)
                        }
                        .navigationBarsPadding(),
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    DodamDivider(
                        type = DividerType.Normal,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Box(
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberBounceIndication(),
                                onClick = {
                                    if (!isFileOpenMode && uiState.files.isEmpty() && uiState.images.isEmpty()) {
                                        filePickerLauncher.launch()
                                    }
                                    isFileOpenMode = !isFileOpenMode
                                },
                            ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(28.dp),
                                imageVector = if (isFileOpenMode) DodamIcons.Close.value else DodamIcons.Plus.value,
                                contentDescription = null,
                                tint = if (isFileOpenMode) DodamTheme.colors.primaryNormal else DodamTheme.colors.labelNormal.copy(alpha = 0.5f),
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        DodamTextButton(
                            onClick = onNextClick,
                            size = TextButtonSize.Large,
                            text = "다음"
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    if (isFileOpenMode) {
                        DodamDivider(
                            type = DividerType.Normal,
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            NoticeTextButton(
                                text = "앨범",
                                count = uiState.images.size.toString(),
                                isSelected = selectTab == NoticeFileType.IMAGE,
                                onClick = {
                                    selectTab = NoticeFileType.IMAGE
                                }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            NoticeTextButton(
                                text = "파일",
                                count = uiState.files.size.toString(),
                                isSelected = selectTab == NoticeFileType.FILE,
                                onClick = {
                                    selectTab = NoticeFileType.FILE
                                }
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            DodamTextButton(
                                onClick = {
                                    if (selectTab == NoticeFileType.IMAGE) {
                                        imagePickerLauncher.launch()
                                    } else {
                                        filePickerLauncher.launch()
                                    }
                                },
                                text = "추가하기",
                                size = TextButtonSize.Medium,
                                type = TextButtonType.Primary,
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        if (selectTab == NoticeFileType.IMAGE) {
                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                uiState.images.fastForEach { item ->
                                    AsyncImage(
                                        model = item.fileUrl,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .border(
                                                width = 1.dp,
                                                shape = DodamTheme.shapes.extraSmall,
                                                color = DodamTheme.colors.lineAlternative,
                                            )
                                            .clip(DodamTheme.shapes.extraSmall),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                        if (selectTab == NoticeFileType.FILE) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(uiState.files.size) { index ->
                                    val item = uiState.files[index]
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                width = 1.dp,
                                                color = DodamTheme.colors.lineNormal,
                                                shape = DodamTheme.shapes.extraSmall,
                                            )
                                            .padding(
                                                horizontal = 10.dp,
                                                vertical = 16.dp
                                            ),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            modifier = Modifier.weight(1f),
                                            text = item.fileName,
                                            style = DodamTheme.typography.labelMedium(),
                                            color = DodamTheme.colors.labelNeutral,
                                        )
                                        Box(
                                            modifier = Modifier
                                                .size(28.dp)
                                                .background(
                                                    color = DodamTheme.colors.primaryNormal,
                                                    shape = CircleShape,
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(24.dp),
                                                imageVector = DodamIcons.File.value,
                                                contentDescription = "파일 아이콘",
                                                tint = DodamTheme.colors.staticWhite
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (uiState.isUploadLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DodamTheme.colors.staticBlack.copy(alpha = 0.3f))
            )
        }
    }
}

@Composable
private fun NoticeTextButton(
    text: String,
    count: String?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .`if`(isSelected) {
                background(
                    color = DodamTheme.colors.fillNormal,
                    shape = DodamTheme.shapes.small,
                )
            }
            .`if`(!isSelected) {
                alpha(0.5f)
            }
            .padding(
                horizontal = 10.dp,
                vertical = 6.dp
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(),
                onClick = onClick,
                enabled = !isSelected
            ),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = text,
            style = DodamTheme.typography.body2Medium(),
            color = DodamTheme.colors.labelNormal
        )
        if (count != null) {
            Text(
                text = count,
                style = DodamTheme.typography.body2Medium(),
                color = DodamTheme.colors.primaryNormal
            )
        }
    }
}