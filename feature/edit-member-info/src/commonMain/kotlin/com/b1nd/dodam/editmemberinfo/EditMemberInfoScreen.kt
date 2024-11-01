package com.b1nd.dodam.editmemberinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.ui.icons.ColoredPencil
import com.b1nd.dodam.ui.icons.DefaultProfile

@Composable
internal fun EditMemberInfoScreen() {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pell by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "정보수정",
                onBackClick = {},
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
                .padding(vertical = 12.dp)
        ) {
            Box {
                if (true) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(DodamTheme.shapes.medium)
                            .size(128.dp),
                        model = "uiState.profile",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .clip(DodamTheme.shapes.medium)
                            .size(128.dp),
                        bitmap = DefaultProfile,
                        contentDescription = "프로필 이미지",
                        contentScale = ContentScale.Crop,
                    )
                }
                Image(
                    imageVector = ColoredPencil,
                    contentDescription = "image",
                    modifier = Modifier.size(20.dp)
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
                    modifier = Modifier.padding(bottom = 24.dp),
                )
                DodamTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = "이메일",
                    onClickRemoveRequest = {
                        email = ""
                    },
                    modifier = Modifier.padding(bottom = 24.dp),
                )
            }
            DodamButton(
                onClick = {},
                text = "수정 완료",
            )
        }
    }
}