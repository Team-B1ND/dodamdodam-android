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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.AvatarSize
import com.b1nd.dodam.designsystem.component.DodamAvatar
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.ui.component.modifier.`if`
import com.b1nd.dodam.ui.icons.Plus
import com.b1nd.dodam.ui.util.addFocusCleaner
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.getPath
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch

@Composable
internal fun EditMemberInfoScreen(
    profileImage: String,
    popBackStack: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val scope = rememberCoroutineScope()
    val context = LocalPlatformContext.current


    var byteArray by remember { mutableStateOf(ByteArray(0)) }
    var platformSpecificFilePath by remember { mutableStateOf("") }

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Multiple,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let {
                    byteArray = it.readByteArray(context)
                    platformSpecificFilePath = it.getPath(context) ?:""
                }
            }
        }
    )

    LaunchedEffect(true){
        platformSpecificFilePath = profileImage
    }

    Scaffold(
        modifier = Modifier.addFocusCleaner(focusManager),
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "정보수정",
                onBackClick = popBackStack,
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DodamTheme.colors.backgroundNeutral)
                .padding(it)
                .padding(horizontal = 16.dp)
                .padding(vertical = 12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val borderColor = DodamTheme.colors.lineAlternative
                Box {
                    DodamAvatar(
                        avatarSize = AvatarSize.XXL,
                        contentDescription = "프로필 이미지",
                        model = if (platformSpecificFilePath == "default") null else platformSpecificFilePath ,
                        modifier = Modifier
                            .`if`(platformSpecificFilePath == "default") {
                                border(
                                    width = 1.dp,
                                    color = borderColor,
                                    shape = CircleShape
                                )
                            },
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        imageVector = Plus,
                        contentDescription = "image",
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.BottomEnd)
                            .clickable {
                                pickerLauncher.launch()
                            }
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
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
                onClick = {},
                text = "수정 완료",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
            )
        }
    }
}