package com.b1nd.dodam.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import com.b1nd.dodam.ui.icons.ColoredCheckmarkCircle
import com.b1nd.dodam.ui.icons.ColoredCheckmarkCircleFilled
import com.b1nd.dodam.ui.icons.ParentImage
import com.b1nd.dodam.ui.icons.StudentImage


@Composable
internal fun SelectRoleScreen(
    onBackClick: () -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier
            .background(DodamTheme.colors.backgroundNormal)
            .statusBarsPadding(),
        topBar = {
            DodamTopAppBar(
                title = "해당하는 곳을\n선택해 주세요",
                onBackClick = onBackClick,
                type = TopAppBarType.Medium
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(DodamTheme.colors.backgroundNormal)
        ) {
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                                RoundedCornerShape(18.dp)
                            )
                            .bounceClick(
                                onClick = { selectedIndex = index },
                                interactionColor = DodamTheme.colors.lineNormal,
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = title,
                                color = DodamTheme.colors.labelNormal,
                                style = if (isSelected) DodamTheme.typography.body1Bold()
                                else DodamTheme.typography.body1Medium(),
                                modifier = if (!isSelected) Modifier.alpha(0.5f) else Modifier
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
                            contentDescription = null
                        )
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            DodamButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                onClick = {  },
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
        onBackClick = {}
    )
}