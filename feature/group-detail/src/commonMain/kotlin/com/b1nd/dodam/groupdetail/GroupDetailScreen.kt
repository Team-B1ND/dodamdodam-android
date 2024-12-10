package com.b1nd.dodam.groupdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.AvatarSize
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DividerType
import com.b1nd.dodam.designsystem.component.DodamAvatar
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDivider
import com.b1nd.dodam.designsystem.component.DodamTag
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TagType
import com.b1nd.dodam.designsystem.foundation.DodamIcons

@Composable
internal fun GroupDetailScreen(
    popBackStack: () -> Unit
) {

    val isJoin = false
    val isPermission = true

    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "",
                onBackClick = popBackStack
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp
                        )
                        .fillMaxWidth()
                        .background(
                            color = DodamTheme.colors.backgroundNormal,
                            shape = DodamTheme.shapes.medium
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "B1ND",
                            style = DodamTheme.typography.heading1Bold(),
                            color = DodamTheme.colors.labelNormal
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberBounceIndication(),
                                onClick = {

                                }
                            ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(18.dp),
                                imageVector = DodamIcons.Menu.value,
                                contentDescription = "메뉴 아이콘",
                                tint = DodamTheme.colors.labelAssistive.copy(
                                    alpha = 0.5f
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "바인드는 도담도담도 개발하구요 서버비도 받아야하구요 회식도 해야해구요 존댓말두 써야하구여 도맘도 개발하구요 CMM도 써야하구여",
                        style = DodamTheme.typography.body1Medium(),
                        color = DodamTheme.colors.labelNeutral
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (isPermission) {
                    Row(
                        modifier = Modifier
                            .padding(
                                top = 12.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(
                                color = DodamTheme.colors.backgroundNormal,
                                shape = DodamTheme.shapes.medium
                            )
                            .padding(
                                horizontal = 16.dp,
                                vertical = 16.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberBounceIndication(),
                                    onClick = {

                                    }
                                ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "가입 신청",
                                style = DodamTheme.typography.headlineBold(),
                                color = DodamTheme.colors.labelStrong
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            DodamTag(
                                text = "12",
                                tagType = TagType.Primary
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(1.dp)
                                .fillMaxHeight()
                                .background(
                                    color = DodamTheme.colors.lineAlternative,
                                ),
                        )

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberBounceIndication(),
                                    onClick = {

                                    }
                                ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "멤버 추가",
                                style = DodamTheme.typography.headlineBold(),
                                color = DodamTheme.colors.labelStrong
                            )
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 12.dp
                        )
                        .weight(1f)
                        .fillMaxWidth()
                        .background(
                            color = DodamTheme.colors.backgroundNormal,
                            shape = DodamTheme.shapes.medium
                        )
                        .padding(16.dp),
                ) {
                    item {
                        Text(
                            text = "멤버",
                            style = DodamTheme.typography.headlineBold(),
                            color = DodamTheme.colors.labelNormal,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "관리자",
                            style = DodamTheme.typography.body2Medium(),
                            color = DodamTheme.colors.labelAlternative
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    items(1) {
                        GroupDetailMemberCard(
                            image = null,
                            name = "test $it",
                            role = when (it % 3) {
                                0 -> "학생"
                                1 -> "선생님"
                                else -> "학부모"
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        DodamDivider(
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "부관리자",
                            style = DodamTheme.typography.body2Medium(),
                            color = DodamTheme.colors.labelAlternative
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    items(3) {
                        GroupDetailMemberCard(
                            image = null,
                            name = "test $it",
                            role = when (it % 3) {
                                0 -> "학생"
                                1 -> "선생님"
                                else -> "학부모"
                            }
                        )
                        if (it != 2) {
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        DodamDivider(
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "멤버",
                            style = DodamTheme.typography.body2Medium(),
                            color = DodamTheme.colors.labelAlternative
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    items(20) {
                        GroupDetailMemberCard(
                            image = null,
                            name = "test $it",
                            role = when (it % 3) {
                                0 -> "학생"
                                1 -> "선생님"
                                else -> "학부모"
                            }
                        )
                        if (it != 3) {
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(144.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.linearGradient(
                            colorStops = arrayOf(
                                0f to DodamTheme.colors.staticWhite.copy(alpha = 0f),
                                0.22f to DodamTheme.colors.staticWhite.copy(alpha = 0.22f),
                                1f to DodamTheme.colors.staticWhite
                            )
                        )
                    )
            ) {
                DodamButton(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp,
                        )
                        .fillMaxWidth()
                        .height(48.dp)
                        .align(Alignment.BottomCenter),
                    text = "가입 신청",
                    onClick = {},
                    buttonSize = ButtonSize.Large,
                    buttonRole = ButtonRole.Primary
                )
            }
        }
    }

}

@Composable
private fun GroupDetailMemberCard(
    image: String?,
    name: String,
    role: String
) {
    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(start = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DodamAvatar(
            avatarSize = AvatarSize.Small,
            model = image,
        )
        Text(
            text = name,
            style = DodamTheme.typography.body1Medium(),
            color = DodamTheme.colors.labelNormal
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = role,
            style = DodamTheme.typography.body2Medium(),
            color = DodamTheme.colors.labelAlternative
        )

    }
}