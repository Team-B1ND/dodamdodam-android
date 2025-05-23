package com.b1nd.dodam.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.dds.animation.bounceClick
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.ui.icons.Checkmark
import com.b1nd.dodam.ui.icons.ParentImage
import com.b1nd.dodam.ui.icons.StudentImage

@Composable
internal fun SelectRoleScreen(onBackClick: () -> Unit, navigateToChildrenManage: () -> Unit, navigateToInfo: () -> Unit) {
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "해당하는 곳을\n선택해 주세요",
                onBackClick = onBackClick,
                type = TopAppBarType.Medium,
            )
        },
        containerColor = DodamTheme.colors.backgroundNormal,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(DodamTheme.colors.backgroundNormal),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                listOf("학생" to StudentImage, "학부모" to ParentImage).forEachIndexed { index, (title, image) ->
                    val isSelected = selectedIndex == index
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .border(
                                2.dp,
                                if (isSelected) DodamTheme.colors.primaryNormal else DodamTheme.colors.lineNeutral,
                                RoundedCornerShape(18.dp),
                            )
                            .bounceClick(
                                onClick = { selectedIndex = index },
                                interactionColor = DodamTheme.colors.lineNormal,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                text = title,
                                color = DodamTheme.colors.labelNormal,
                                style = if (isSelected) {
                                    DodamTheme.typography.body1Bold()
                                } else {
                                    DodamTheme.typography.body1Medium()
                                },
                                modifier = if (!isSelected) Modifier.alpha(0.5f) else Modifier,
                            )
                            if (isSelected) {
                                Image(imageVector = Checkmark, contentDescription = null)
                            }
                        }
                        Image(
                            modifier = Modifier
                                .size(128.dp)
                                .align(Alignment.BottomCenter),
                            bitmap = image,
                            contentDescription = null,
                        )
                    }
                }
            }
            DodamButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                onClick = {
                    if (selectedIndex == 0) {
                        navigateToInfo()
                    } else {
                        navigateToChildrenManage()
                    }
                },
                text = "다음",
                buttonRole = ButtonRole.Primary,
                buttonSize = ButtonSize.Large,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SelectRoleScreen(
        onBackClick = {},
        navigateToChildrenManage = {},
        navigateToInfo = {},
    )
}
