package com.b1nd.dodam.noticecreate.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.b1nd.dodam.data.division.model.DivisionOverview
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.DividerType
import com.b1nd.dodam.designsystem.component.DodamDivider
import com.b1nd.dodam.designsystem.component.DodamTextButton
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.component.modifier.`if`
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun NoticeCreateSecondScreen(
    isLoading: Boolean,
    selectDivisions: ImmutableList<DivisionOverview>,
    divisions: ImmutableList<DivisionOverview>,
    popBackStack: () -> Unit,
    onClickCategory: (division: DivisionOverview) -> Unit,
    onClickSuccess: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                DodamTopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    title = "공지를 보낼\n그룹을 선택해 주세요",
                    type = TopAppBarType.Medium,
                    onBackClick = popBackStack,
                )
            },
            bottomBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .navigationBarsPadding(),
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    DodamDivider(
                        type = DividerType.Normal,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DodamTextButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = onClickSuccess,
                        text = "완료",
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            },
            containerColor = DodamTheme.colors.backgroundNormal,
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                    )
                    .padding(it),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                divisions.fastForEach {
                    NoticeCreateCategory(
                        text = it.name,
                        isSelect = it in selectDivisions,
                        onClick = {
                            onClickCategory(it)
                        },
                    )
                }
            }
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DodamTheme.colors.staticBlack.copy(alpha = 0.3f)),
            )
        }
    }
}

@Composable
private fun NoticeCreateCategory(text: String, isSelect: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(
                    radius = RoundedCornerShape(31.dp),
                ),
                onClick = onClick,
            )
            .background(
                color = if (isSelect) DodamTheme.colors.primaryNormal else DodamTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(31.dp),
            )
            .`if`(!isSelect) {
                border(
                    width = 1.dp,
                    color = DodamTheme.colors.lineAlternative,
                    shape = RoundedCornerShape(31.dp),
                )
            }
            .padding(
                vertical = 8.dp,
                horizontal = 18.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(14.dp),
            imageVector = if (isSelect) DodamIcons.Checkmark.value else DodamIcons.Plus.value,
            contentDescription = null,
            tint = if (isSelect) DodamTheme.colors.staticWhite else DodamTheme.colors.labelAlternative,
        )

        Text(
            text = text,
            style = DodamTheme.typography.labelMedium(),
            color = if (isSelect) DodamTheme.colors.staticWhite else DodamTheme.colors.labelAlternative,
        )
    }
}
