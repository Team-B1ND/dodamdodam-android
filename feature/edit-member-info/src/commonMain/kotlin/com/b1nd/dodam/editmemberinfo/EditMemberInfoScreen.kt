package com.b1nd.dodam.editmemberinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.AvatarSize
import com.b1nd.dodam.designsystem.component.DodamAvatar
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.editmemberinfo.model.EditMemberInfoSideEffect
import com.b1nd.dodam.editmemberinfo.viewmodel.EditMemberInfoViewModel
import com.b1nd.dodam.ui.component.modifier.`if`
import com.b1nd.dodam.ui.util.addFocusCleaner
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.getPath
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun EditMemberInfoScreen(
    viewModel: EditMemberInfoViewModel = koinViewModel(),
    profileImage: String,
    popBackStack: () -> Unit,
    name: String,
    email: String,
    phone: String,
) {
    var name by remember { mutableStateOf(name) }
    var email by remember { mutableStateOf(email) }
    var phone by remember { mutableStateOf(phone) }
    val focusManager = LocalFocusManager.current

    val uiState by viewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope()
    val context = LocalPlatformContext.current

    var byteArray by remember { mutableStateOf(ByteArray(0)) }
    var platformSpecificFilePath by remember { mutableStateOf("") }
    var fileName by remember { mutableStateOf("") }

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Multiple,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let { file ->
                    byteArray = file.readByteArray(context)
                    platformSpecificFilePath = file.getPath(context) ?: ""
                    fileName = file.getName(context) ?: ""
                    viewModel.setProfile(profileImage)
                    viewModel.fileUpload(
                        fileByteArray = byteArray,
                        fileMimeType = fileName.split(".")[1],
                        fileName = fileName.split(".")[0],
                    )
                }
            }
        },
    )

    LaunchedEffect(true) {
        platformSpecificFilePath = profileImage
        viewModel.setProfile(
            if (profileImage == "default") null else profileImage,
        )
        viewModel.sideEffect.collect {
            when (it) {
                EditMemberInfoSideEffect.SuccessEditMemberInfo -> {
                    popBackStack()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.addFocusCleaner(focusManager),
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "정보수정",
                onBackClick = popBackStack,
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DodamTheme.colors.backgroundNeutral)
                .padding(it)
                .padding(horizontal = 16.dp)
                .padding(vertical = 12.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val borderColor = DodamTheme.colors.lineAlternative
                Box {
                    DodamAvatar(
                        avatarSize = AvatarSize.XXL,
                        contentDescription = "프로필 이미지",
                        model = uiState.image,
                        modifier = Modifier
                            .`if`(uiState.image.isNullOrEmpty()) {
                                border(
                                    width = 1.dp,
                                    color = borderColor,
                                    shape = CircleShape,
                                )
                            },
                        contentScale = ContentScale.Crop,
                    )
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.BottomEnd)
                            .background(
                                color = DodamTheme.colors.primaryNormal,
                                shape = CircleShape,
                            )
                            .clickable {
                                pickerLauncher.launch()
                            },
                    ) {
                        Image(
                            imageVector = DodamIcons.Plus.value,
                            contentDescription = "plus",
                            colorFilter = ColorFilter.tint(DodamTheme.colors.staticWhite),
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.Center),
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                ) {
                    DodamTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        label = "이름",
                        onClickRemoveRequest = {
                            name = ""
                        },
                    )
                    Spacer(Modifier.height(24.dp))
                    DodamTextField(
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        label = "이메일",
                        onClickRemoveRequest = {
                            email = ""
                        },
                        modifier = Modifier,
                    )
                    Spacer(Modifier.height(24.dp))
                    DodamTextField(
                        value = phone,
                        onValueChange = {
                            phone = it
                        },
                        label = "전화번호",
                        onClickRemoveRequest = {
                            phone = ""
                        },
                        modifier = Modifier,
                    )
                    Spacer(Modifier.height(24.dp))
                }
            }
            DodamButton(
                onClick = {
                    if (!uiState.isLoading) {
                        viewModel.editMember(
                            email = email,
                            name = name,
                            phone = phone,
                            profileImage = uiState.image,
                        )
                    }
                },
                text = "수정 완료",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                enabled = !uiState.isLoading,
                loading = uiState.isLoading
            )
        }
    }
}
