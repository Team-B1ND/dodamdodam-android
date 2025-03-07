package com.b1nd.dodam.buspreset

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ActionIcon
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamEmpty
import com.b1nd.dodam.designsystem.component.DodamModalBottomSheet
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.icons.ColoredBus
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusPresetScreen(
    popBackStack: () -> Unit,
    navigateToBusPresetCreate: () -> Unit
) {

    var isShowBottomSheet by remember { mutableStateOf(false) }

    if (isShowBottomSheet) {
        DodamModalBottomSheet(
            onDismissRequest = {
                isShowBottomSheet = false
            },
            title = {
                Text(
                    text = "동대구역 1호",
                    style = DodamTheme.typography.heading1Bold(),
                    color = DodamTheme.colors.labelNormal,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "동대구역에 하차합니다.",
                    style = DodamTheme.typography.headlineMedium(),
                    color = DodamTheme.colors.labelAssistive,
                )
            },
            content = {
                Text(
                    text = "출발 시간: 13:00",
                    style = DodamTheme.typography.headlineMedium(),
                    color = DodamTheme.colors.labelAssistive,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "소요 시간: 01:00",
                    style = DodamTheme.typography.headlineMedium(),
                    color = DodamTheme.colors.labelAssistive,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "인원 제한: 40",
                    style = DodamTheme.typography.headlineMedium(),
                    color = DodamTheme.colors.labelAssistive,
                )
                Spacer(modifier = Modifier.height(24.dp))
                DodamButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        isShowBottomSheet = false
                    },
                    text = "프리셋 사용",
                    buttonSize = ButtonSize.Large,
                    buttonRole = ButtonRole.Primary,
                )
            },
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
        )
    }

    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "버스 등록 프리셋",
                type = TopAppBarType.Small,
                onBackClick = popBackStack,
                actionIcons = persistentListOf(
                    ActionIcon(
                        icon = DodamIcons.Plus,
                        onClick = navigateToBusPresetCreate,
                    )
                )
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                DodamEmpty(
                    imageVector = ColoredBus,
                    contentDescription = "버스 아이콘",
                    title = "프리셋이 아직 없어요.",
                    buttonText = "새 버스 등록 프리셋 생성",
                    onClick = {

                    }
                )
            }
            items(5) {
                BusPresetItem(
                    name = "bus $it",
                    onClick = {
                        isShowBottomSheet = true
                    }
                )
            }
        }
    }
}

@Composable
private fun BusPresetItem(
    name: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(),
                onClick = onClick,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .weight(1f),
            text = name,
            style = DodamTheme.typography.body1Medium(),
            color = DodamTheme.colors.labelNormal,
            textAlign = TextAlign.Start,
        )
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = DodamIcons.ChevronRight.value,
            contentDescription = "오른쪽 화살표",
            tint = DodamTheme.colors.labelAssistive
        )
    }
}