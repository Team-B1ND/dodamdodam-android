package com.b1nd.dodam.point.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.data.point.model.PointReason
import com.b1nd.dodam.data.point.model.PointType
import com.b1nd.dodam.data.point.model.ScoreType
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DividerType
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDivider
import com.b1nd.dodam.designsystem.component.DodamEmpty
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.point.getDodamSegment
import com.b1nd.dodam.point.model.PointLoadingUiState
import com.b1nd.dodam.point.model.PointStudentModel
import com.b1nd.dodam.ui.component.DodamMember
import com.b1nd.dodam.ui.component.modifier.dropShadow
import com.b1nd.dodam.ui.component.modifier.`if`
import com.b1nd.dodam.ui.icons.ColoredBullseye
import com.b1nd.dodam.ui.icons.ColoredCheckmarkCircle
import com.b1nd.dodam.ui.icons.ColoredTrophy
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GiveScreen(
    uiState: PointLoadingUiState,
    onClickGivePoint: (ImmutableList<PointStudentModel>, PointReason) -> Unit,
    reload: () -> Unit,
    popBackStack: () -> Unit,
) {
    var selectPointType by remember { mutableStateOf("학교") }
    var selectScoreType by remember { mutableStateOf("상점") }
    var selectReason: PointReason? by remember { mutableStateOf(null) }

    val onSelectGrade: (String) -> Unit = {
        selectReason = null
        selectPointType = it
    }
    val onSelectRoom: (String) -> Unit = {
        selectReason = null
        selectScoreType = it
    }

    val nowPointType by remember {
        derivedStateOf {
            if (selectPointType == "학교") PointType.SCHOOL else PointType.DORMITORY
        }
    }

    val nowScoreType by remember {
        derivedStateOf {
            if (selectScoreType == "상점") ScoreType.BONUS else ScoreType.MINUS
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Scaffold(
            topBar = {
                DodamTopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    title = "상벌점 부여",
                    onBackClick = popBackStack,
                )
            },
            containerColor = DodamTheme.colors.backgroundNormal,
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                DodamSegmentedButton(
                    modifier = Modifier.fillMaxWidth(),
                    segments = persistentListOf(
                        getDodamSegment("학교", selectPointType, onSelectGrade),
                        getDodamSegment("기숙사", selectPointType, onSelectGrade),
                    ),
                )
                Spacer(modifier = Modifier.height(12.dp))
                DodamSegmentedButton(
                    modifier = Modifier.fillMaxWidth(),
                    segments = persistentListOf(
                        getDodamSegment("상점", selectScoreType, onSelectRoom),
                        getDodamSegment("벌점", selectScoreType, onSelectRoom),
                    ),
                )
                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(
                            weight = 1f,
                            fill = false,
                        ),
                ) {
                    Text(
                        text = "$selectScoreType 목록",
                        style = DodamTheme.typography.headlineBold(),
                        color = DodamTheme.colors.labelNormal,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                       when (uiState) {
                           PointLoadingUiState.Error -> {
                               item {
                                   DodamEmpty(
                                       modifier = Modifier.fillMaxWidth(),
                                       onClick = reload,
                                       title = "$selectPointType 목록 을 불러올 수 없어요.",
                                       buttonText = "다시 불러오기",
                                       border = BorderStroke(
                                           width = 1.dp,
                                           color = DodamTheme.colors.lineAlternative
                                       )
                                   )
                               }
                           }
                           PointLoadingUiState.Loading -> {}
                           is PointLoadingUiState.Success -> {
                               // TODO 스페이싱 추가하기
                               items(
                                   items = uiState.reasons
                                       .filter {
                                           it.scoreType == nowScoreType && it.pointType == nowPointType
                                       },
                                   key = { it.id },
                               ) {
                                   Row(
                                       modifier = Modifier
                                           .fillMaxWidth()
                                           .clickable(
                                               interactionSource = remember { MutableInteractionSource() },
                                               indication = rememberBounceIndication(),
                                               onClick = {
                                                   selectReason = if (selectReason == it) null else it
                                               },
                                           ),
                                       verticalAlignment = Alignment.CenterVertically,
                                   ) {
                                       Box(
                                           modifier = Modifier
                                               .size(40.dp)
                                               .background(
                                                   color = DodamTheme.colors.backgroundAlternative,
                                                   shape = CircleShape,
                                               ),
                                           contentAlignment = Alignment.Center,
                                       ) {
                                           Image(
                                               modifier = Modifier
                                                   .size(24.dp)
                                                   .`if`(nowScoreType != ScoreType.BONUS) {
                                                       offset(x = 1.dp)
                                                   },
                                               imageVector = if (nowScoreType == ScoreType.BONUS) ColoredTrophy else ColoredBullseye,
                                               contentDescription = null,
                                           )
                                       }
                                       Spacer(modifier = Modifier.width(8.dp))
                                       Text(
                                           text = it.reason,
                                           style = DodamTheme.typography.headlineMedium(),
                                           color = DodamTheme.colors.labelNormal,
                                       )

                                       if (selectReason == it) {
                                           Spacer(modifier = Modifier.weight(1f))
                                           Image(
                                               modifier = Modifier.size(24.dp),
                                               imageVector = ColoredCheckmarkCircle,
                                               contentDescription = null,
                                               colorFilter = ColorFilter.tint(DodamTheme.colors.primaryNormal),
                                           )
                                       }
                                   }
                               }
                           }
                       }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                DodamDivider(
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth(),
                    type = DividerType.Thick,
                )
                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(
                            weight = 1f,
                            fill = false,
                        ),
                ) {
                    Text(
                        text = "학생 목록",
                        style = DodamTheme.typography.headlineBold(),
                        color = DodamTheme.colors.labelNormal,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        when (uiState) {
                            PointLoadingUiState.Error -> {
                                item {
                                    DodamEmpty(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = reload,
                                        title = "학생 목록 을 불러올 수 없어요.",
                                        buttonText = "다시 불러오기",
                                    )
                                }
                            }
                            PointLoadingUiState.Loading -> {}
                            is PointLoadingUiState.Success -> {
                                items(
                                    items = uiState.students
                                        .filter {
                                            it.selected
                                        },
                                    key = { it.id },
                                ) {
                                    DodamMember(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        icon = it.profileImage,
                                        name = it.name,
                                        content = { },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        if (selectReason != null) {
            FakeBottomSheet(
                modifier = Modifier.align(Alignment.BottomCenter),
                title = {
                    Text(
                        text = "상벌점 정보",
                        style = DodamTheme.typography.heading1Bold(),
                        color = DodamTheme.colors.labelNormal,
                    )
                },
                content = {
                    GiveCategoryComponent(
                        category = "상벌점 종류",
                        content = selectScoreType,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    GiveCategoryComponent(
                        category = "발급 점수",
                        content = "${selectReason!!.score}점",
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    GiveCategoryComponent(
                        category = "발급 사유",
                        content = selectReason!!.reason,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    DodamButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "부여하기",
                        buttonRole = if (nowScoreType == ScoreType.BONUS) ButtonRole.Primary else ButtonRole.Negative,
                        buttonSize = ButtonSize.Large,
                        onClick = {
                            if (uiState is PointLoadingUiState.Success) {
                                onClickGivePoint(
                                    uiState.students.filter { it.selected }.toImmutableList(),
                                    selectReason!!,
                                )
                            }
                        },
                    )
                },
                space = 16.dp,
            )
        }
    }
}

@Composable
private fun GiveCategoryComponent(modifier: Modifier = Modifier, category: String, content: String) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = category,
            style = DodamTheme.typography.headlineMedium(),
            color = DodamTheme.colors.labelAssistive,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = content,
            style = DodamTheme.typography.headlineMedium(),
            color = DodamTheme.colors.labelNeutral,
            textAlign = TextAlign.Right,
        )
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
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(
                blur = 8.dp,
                offsetY = (-4).dp,
                color = DodamTheme.colors.staticBlack.copy(alpha = 0.1f),
            )
            .background(
                color = DodamTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(
                    topStart = 28.dp,
                    topEnd = 28.dp,
                ),
            ),
//            .dropShadow()
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    top = 24.dp,
                    bottom = 16.dp,
                ),
        ) {
            Box(
                modifier = Modifier
                    .width(64.dp)
                    .height(6.dp)
                    .background(
                        color = DodamTheme.colors.fillAlternative,
                        shape = DodamTheme.shapes.extraSmall,
                    ),
            )
        }
        Column(
            modifier = Modifier
                .padding(
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
