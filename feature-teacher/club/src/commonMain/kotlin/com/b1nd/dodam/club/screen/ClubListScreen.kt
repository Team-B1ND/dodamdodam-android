@file:Suppress("UNREACHABLE_CODE")

package com.b1nd.dodam.club.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.b1nd.dodam.club.model.Club
import com.b1nd.dodam.club.model.ClubPendingUiState
import com.b1nd.dodam.club.model.ClubState
import com.b1nd.dodam.club.model.ClubType
import com.b1nd.dodam.club.model.ClubUiState
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamLoadingDots
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTextButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TextButtonSize
import com.b1nd.dodam.designsystem.component.TextButtonType
import com.b1nd.dodam.ui.component.modifier.dropShadow
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.b1nd.dodam.ui.icons.ColoredCheckmarkCircleFilled
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun ClubListScreen(
    state: ClubUiState,
    popBackStack: () -> Unit,
    selectClubList: (Long, String, ClubType, String) -> Unit,
    selectDetailClub: (Long, Club) -> Unit,
    selectAllowButton: (Long, ClubState, String?) -> Unit,
) {
    var clubTypeIndex by remember { mutableIntStateOf(0) }
    val clubTypeList = listOf(
        "창체",
        "자율",
    )
    val clubTypeItem = List(2) { index ->
        DodamSegment(
            selected = clubTypeIndex == index,
            text = clubTypeList[index],
            onClick = { clubTypeIndex = index },
        )
    }.toImmutableList()
    var clubStateTypeIndex by remember { mutableIntStateOf(0) }
    val clubStateTypeList = listOf(
        "요청중",
        "승인됨",
    )
    val clubStateTypeItem = List(2) { index ->
        DodamSegment(
            selected = clubStateTypeIndex == index,
            text = clubStateTypeList[index],
            onClick = { clubStateTypeIndex = index },
        )
    }.toImmutableList()
    var selectedItemIndex: Club? by remember { mutableStateOf(null) }

    var selectedReject by remember { mutableStateOf(false) }
    var rejectReason by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                DodamTopAppBar(
                    title = "동아리",
                    modifier = Modifier.statusBarsPadding(),
                    onBackClick = popBackStack,
                )
            },
            containerColor = DodamTheme.colors.backgroundNeutral,
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(it),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                ) {
                    DodamSegmentedButton(
                        segments = clubStateTypeItem,
                        modifier = Modifier.padding(top = 16.dp),
                    )
                    DodamSegmentedButton(
                        segments = clubTypeItem,
                        modifier = Modifier.padding(top = 16.dp),
                    )
                    when (val data = state.clubPendingUiState) {
                        ClubPendingUiState.Error -> {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(top = 20.dp).background(
                                    color = DodamTheme.colors.backgroundNormal,
                                    shape = RoundedCornerShape(12.dp),
                                ),
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center)
                                        .padding(vertical = 16.dp),
                                    text = "아직 등록된 동아리가 없어요.",
                                    style = DodamTheme.typography.labelMedium(),
                                    color = DodamTheme.colors.labelAlternative,
                                )
                            }
                        }

                        ClubPendingUiState.Loading -> {
                            Column(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                DodamLoadingClub()
                                DodamLoadingClub()
                            }
                        }

                        is ClubPendingUiState.Success -> {
                            val originClubs =
                                if (clubTypeIndex == 0) data.clubPendingList.creativeClubs else data.clubPendingList.selfClubs
                            val clubs =
                                originClubs.filter { ww -> if (clubStateTypeIndex == 0) ww.state == ClubState.PENDING else ww.state == ClubState.ALLOWED }
                            if (clubs.isEmpty()) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
                                        .background(
                                            color = DodamTheme.colors.backgroundNormal,
                                            shape = RoundedCornerShape(12.dp),
                                        ),
                                ) {
                                    Text(
                                        modifier = Modifier.align(Alignment.Center)
                                            .padding(vertical = 16.dp),
                                        text = "아직 등록된 동아리가 없어요.",
                                        style = DodamTheme.typography.labelMedium(),
                                        color = DodamTheme.colors.labelAlternative,
                                    )
                                }
                            } else {
                                LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
                                    items(clubs.size) { index ->
                                        DodamClub(
                                            modifier = Modifier.padding(bottom = 12.dp).clickable {
                                                if (clubStateTypeIndex == 0) {
                                                    selectedItemIndex =
                                                        if (selectedItemIndex == clubs[index]) {
                                                            null
                                                        } else {
                                                            clubs[index]
                                                        }
                                                    selectClubList(
                                                        clubs[index].id.toLong(),
                                                        clubs[index].name,
                                                        clubs[index].type,
                                                        clubs[index].shortDescription,
                                                    )
                                                } else {
                                                    selectDetailClub(
                                                        clubs[index].id.toLong(),
                                                        clubs[index]
                                                    )
                                                }
                                            },
                                            club = clubs[index],
                                            isSelected = clubs[index] == selectedItemIndex,
                                            onDetailButtonClick = {
                                                selectDetailClub(
                                                    clubs[index].id.toLong(),
                                                    clubs[index]
                                                )
                                            },
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (selectedItemIndex != null) {
            FakeBottomSheet(
                modifier = Modifier.align(Alignment.BottomCenter),
                title = {
                    Text(
                        text = "${state.detailClub.name} 개설 신청",
                        style = DodamTheme.typography.heading1Bold(),
                        color = DodamTheme.colors.labelNormal,
                    )
                },
                content = {
                    if (state.detailClub.isLoading) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(134.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            DodamLoadingDots()
                        }
                    } else {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    "주제",
                                    style = DodamTheme.typography.headlineMedium(),
                                    color = DodamTheme.colors.labelAssistive,
                                )
                                Text(
                                    state.detailClub.type.type,
                                    style = DodamTheme.typography.headlineMedium(),
                                    color = DodamTheme.colors.labelNeutral,
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    "부장",
                                    style = DodamTheme.typography.headlineMedium(),
                                    color = DodamTheme.colors.labelAssistive,
                                )
                                Text(
                                    state.detailClub.leader ?: "없음",
                                    style = DodamTheme.typography.headlineMedium(),
                                    color = DodamTheme.colors.labelNeutral,
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    "간략 소개",
                                    style = DodamTheme.typography.headlineMedium(),
                                    color = DodamTheme.colors.labelAssistive,
                                )
                                Text(
                                    state.detailClub.shortDescription,
                                    style = DodamTheme.typography.headlineMedium(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = DodamTheme.colors.labelNeutral,
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                DodamButton(
                                    modifier = Modifier.weight(0.35f),
                                    text = "거절하기",
                                    buttonRole = ButtonRole.Assistive,
                                    onClick = {
                                        selectedReject = true
                                    },
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                DodamButton(
                                    modifier = Modifier.weight(0.666667f),
                                    text = "승인하기",
                                    onClick = {
                                        selectAllowButton(
                                            state.detailClub.id,
                                            ClubState.ALLOWED,
                                            null,
                                        )
                                        selectedItemIndex = null
                                    },
                                )
                            }
                        }
                    }
                },
                space = 16.dp,
            )
        }

        if (selectedReject) {
            Box(
                modifier = Modifier.fillMaxSize().alpha(0.3f).background(
                    color = DodamTheme.colors.staticBlack,
                ).clickable {
                    selectedReject = false
                    rejectReason = ""
                },
            )
            Column(
                modifier = Modifier.align(Alignment.Center).fillMaxWidth(0.8f).height(200.dp)
                    .background(
                        shape = RoundedCornerShape(16.dp),
                        color = DodamTheme.colors.backgroundNormal,
                    ).padding(horizontal = 24.dp, vertical = 26.dp),
            ) {
                Text(
                    text = "거절 사유를 입력해주세요.",
                    style = DodamTheme.typography.heading1Bold(),
                    color = DodamTheme.colors.labelNormal,
                )
                DodamTextField(
                    value = rejectReason,
                    onValueChange = {
                        rejectReason = it
                    },
                    singleLine = true,
                    onClickRemoveRequest = { rejectReason = "" },
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    DodamButton(
                        modifier = Modifier.weight(1f),
                        buttonRole = ButtonRole.Assistive,
                        onClick = {
                            selectedReject = false
                            rejectReason = ""
                        },
                        text = "취소",
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    DodamButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            selectAllowButton(
                                state.detailClub.id,
                                ClubState.REJECTED,
                                rejectReason,
                            )
                            selectedItemIndex = null
                            selectedReject = false
                            rejectReason = ""
                        },
                        text = "확인",
                    )
                }
            }
        }
    }
}

// TODO : 컴포넌트로 뺄 예정입니다.
@Composable
private fun DodamClub(modifier: Modifier = Modifier, club: Club, isSelected: Boolean, onDetailButtonClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(86.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (club.image.isNullOrEmpty()) {
            Box(
                modifier = Modifier.width(83.dp).height(86.dp)
                    .background(
                        brush = shimmerEffect(),
                        shape = RoundedCornerShape(8.dp),
                    ),
            )
        } else {
            AsyncImage(
                modifier = Modifier.clip(shape = RoundedCornerShape(8.dp)).width(83.dp)
                    .height(86.dp),
                model = club.image,
                contentDescription = "동아리",
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = club.subject,
                style = DodamTheme.typography.caption1Medium(),
                color = DodamTheme.colors.labelAlternative,
            )
            Row {
                Text(
                    text = club.name,
                    style = DodamTheme.typography.headlineBold(),
                    color = DodamTheme.colors.labelNormal,
                )
                DodamTextButton(
                    onClick = onDetailButtonClick,
                    text = "자세히보기",
                    size = TextButtonSize.Small,
                    type = TextButtonType.Primary,
                    showUnderline = false,
                )
            }
            Text(
                text = club.description,
                style = DodamTheme.typography.body2Medium(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = DodamTheme.colors.labelNormal,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (isSelected) {
            Image(
                modifier = Modifier.size(24.dp),
                imageVector = ColoredCheckmarkCircleFilled,
                contentDescription = null,
                colorFilter = ColorFilter.tint(DodamTheme.colors.primaryNormal),
            )
        }
    }
}

@Composable
private fun DodamLoadingClub(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(86.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.width(83.dp).height(86.dp)
                .background(
                    brush = shimmerEffect(),
                    shape = RoundedCornerShape(8.dp),
                ),
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Box(
                modifier = Modifier.width(32.dp).height(17.dp)
                    .background(
                        brush = shimmerEffect(),
                        shape = RoundedCornerShape(4.dp),
                    ),
            )
            Spacer(modifier = Modifier.height(1.dp))
            Row {
                Box(
                    modifier = Modifier.width(46.dp).height(27.dp)
                        .background(
                            brush = shimmerEffect(),
                            shape = RoundedCornerShape(4.dp),
                        ),
                )
            }
            Spacer(modifier = Modifier.height(1.dp))
            Box(
                modifier = Modifier.width(273.dp).height(21.dp)
                    .background(
                        brush = shimmerEffect(),
                        shape = RoundedCornerShape(4.dp),
                    ),
            )
        }
    }
}

@Composable
private fun FakeBottomSheet(
    modifier: Modifier = Modifier,
    title: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    space: Dp = 4.dp,
) {
    Column(
        modifier = modifier.fillMaxWidth().dropShadow(
            blur = 8.dp,
            offsetY = (-4).dp,
            color = DodamTheme.colors.staticBlack.copy(alpha = 0.1f),
        ).background(
            color = DodamTheme.colors.backgroundNormal,
            shape = RoundedCornerShape(
                topStart = 28.dp,
                topEnd = 28.dp,
            ),
        ),
//            .dropShadow()
    ) {
        Surface(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(
                top = 24.dp,
                bottom = 16.dp,
            ),
        ) {
            Box(
                modifier = Modifier.width(64.dp).height(6.dp).background(
                    color = DodamTheme.colors.fillAlternative,
                    shape = DodamTheme.shapes.extraSmall,
                ),
            )
        }
        Column(
            modifier = Modifier.padding(
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp,
            ),
        ) {
            title()
            Spacer(modifier = Modifier.height(space))
            content()
            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}
