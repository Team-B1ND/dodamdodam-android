package com.b1nd.dodam.nightstudy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ActionIcon
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.DividerType
import com.b1nd.dodam.designsystem.component.DodamButtonDialog
import com.b1nd.dodam.designsystem.component.DodamDefaultTopAppBar
import com.b1nd.dodam.designsystem.component.DodamDivider
import com.b1nd.dodam.designsystem.component.DodamEmpty
import com.b1nd.dodam.designsystem.component.DodamLinerProgressIndicator
import com.b1nd.dodam.designsystem.component.DodamTag
import com.b1nd.dodam.designsystem.component.TagType
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.nightstudy.viewmodel.NightStudyUiState
import com.b1nd.dodam.nightstudy.viewmodel.NightStudyViewModel
import com.b1nd.dodam.ui.effect.shimmerEffect
import java.time.temporal.ChronoUnit
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun NightStudyScreen(
    onAddClick: () -> Unit,
    showToast: (String, String) -> Unit,
    refresh: () -> Boolean,
    dispose: () -> Unit,
    viewModel: NightStudyViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val nightStudyScreenState = rememberNightStudyScreenState()

    var playOnlyOnce by rememberSaveable { mutableStateOf(true) }

    var showDialog by remember { mutableStateOf(false) }
    var reason by remember { mutableStateOf("") }
    var id by remember { mutableLongStateOf(0) }

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = false
            viewModel.getMyNigthStudy()
        },
    )

    DisposableEffect(Unit) {
        if (refresh()) viewModel.getMyNigthStudy()

        onDispose(dispose)
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
        ) {
            DodamButtonDialog(
                confirmButtonText = "삭제",
                confirmButton = { viewModel.deleteNightStudy(id) },
                confirmButtonRole = ButtonRole.Negative,
                dismissButton = { showDialog = false },
                dismissButtonRole = ButtonRole.Primary,
                title = "정말 삭제하시겠어요?",
                body = reason,
            )
        }
    }

    Scaffold(
        containerColor = DodamTheme.colors.backgroundNeutral,
        topBar = {
            Column {
                DodamDefaultTopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    title = "심야 자습",
                    actionIcons = persistentListOf(
                        ActionIcon(
                            icon = DodamIcons.Plus,
                            onClick = onAddClick,
                        ),
                    ),
                )
                AnimatedVisibility(nightStudyScreenState.canScrollBackward) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(DodamTheme.colors.fillNeutral),
                    )
                }
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DodamTheme.colors.backgroundNeutral)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .pullRefresh(pullRefreshState),
            contentAlignment = Alignment.TopCenter,
        ) {
            PullRefreshIndicator(
                modifier = Modifier
                    .zIndex(1f),
                refreshing = isRefreshing,
                state = pullRefreshState,
            )
            Column {
                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = nightStudyScreenState.lazyListState,
                ) {
                    when (val nightStudyUiState = uiState) {
                        is NightStudyUiState.Success -> {
                            if (nightStudyUiState.nightStudies.isNotEmpty()) {
                                items(
                                    items = nightStudyUiState.nightStudies,
                                    key = { it.id },
                                ) { nightStudy ->

                                    LaunchedEffect(Unit) {
                                        playOnlyOnce = false
                                    }

                                    when (nightStudy.status) {
                                        Status.PENDING -> {
                                            NightStudyApplyCell(
                                                tagType = TagType.Secondary,
                                                reason = nightStudy.content,
                                                startAt = nightStudy.startAt,
                                                endAt = nightStudy.endAt,
                                                phoneReason = nightStudy.reasonForPhone,
                                                onTrashClick = {
                                                    id = nightStudy.id
                                                    reason = nightStudy.content
                                                    showDialog = true
                                                },
                                                playOnlyOnce = playOnlyOnce,
                                            )
                                        }
                                        Status.ALLOWED -> {
                                            NightStudyApplyCell(
                                                tagType = TagType.Primary,
                                                reason = nightStudy.content,
                                                startAt = nightStudy.startAt,
                                                endAt = nightStudy.endAt,
                                                phoneReason = nightStudy.reasonForPhone,
                                                onTrashClick = {
                                                    id = nightStudy.id
                                                    reason = nightStudy.content
                                                    showDialog = true
                                                },
                                                playOnlyOnce = playOnlyOnce,
                                            )
                                        }
                                        Status.REJECTED -> {
                                            NightStudyApplyRejectCell(
                                                reason = nightStudy.content,
                                                rejectReason = nightStudy.rejectReason ?: "",
                                                onTrashClick = {
                                                    id = nightStudy.id
                                                    reason = nightStudy.content
                                                    showDialog = true
                                                },
                                            )
                                        }
                                    }
                                }
                            } else {
                                item {
                                    DodamEmpty(
                                        onClick = onAddClick,
                                        title = "아직 신청한 심야 자습이 없어요.",
                                        buttonText = "심야 자습 신청하기",
                                    )
                                }
                            }
                        }

                        is NightStudyUiState.Loading -> {
                            item {
                                androidx.compose.material3.Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = DodamTheme.shapes.large,
                                    color = DodamTheme.colors.backgroundNormal,
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(
                                                vertical = 16.dp,
                                                horizontal = 12.dp,
                                            ),
                                        verticalArrangement = Arrangement.spacedBy(12.dp),
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .width(66.dp)
                                                    .height(28.dp)
                                                    .background(
                                                        brush = shimmerEffect(),
                                                        shape = CircleShape
                                                    )
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            Box(
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .background(
                                                        brush = shimmerEffect(),
                                                        shape = RoundedCornerShape(4.dp)
                                                    )
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(24.dp)
                                                .background(
                                                    brush = shimmerEffect(),
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                        )
                                        DodamDivider(type = DividerType.Normal)
                                        Box(
                                            modifier = Modifier
                                                .width(120.dp)
                                                .height(28.dp)
                                                .background(
                                                    brush = shimmerEffect(),
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                        )

                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalArrangement = Arrangement.spacedBy(4.dp),
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(14.dp)
                                                    .background(
                                                        brush = shimmerEffect(),
                                                        shape = RoundedCornerShape(8.dp)
                                                    )
                                            )
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .width(101.dp)
                                                        .height(24.dp)
                                                        .background(
                                                            brush = shimmerEffect(),
                                                            shape = CircleShape
                                                        )
                                                )

                                                Spacer(modifier = Modifier.weight(1f))
                                                Box(
                                                    modifier = Modifier
                                                        .width(101.dp)
                                                        .height(24.dp)
                                                        .background(
                                                            brush = shimmerEffect(),
                                                            shape = CircleShape
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        is NightStudyUiState.Error -> showToast(
                            "ERROR",
                            "심야 자습을 불러올 수 없어요",
                        )

                        is NightStudyUiState.SuccessDelete -> {
                            showDialog = false
                            showToast("SUCCESS", "심야 자습을 삭제했어요")
                        }

                        is NightStudyUiState.FailDelete -> {
                            showDialog = false
                            showToast("ERROR", "심야 자습 삭제를 실패했어요")
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun NightStudyApplyCell(
    modifier: Modifier = Modifier,
    tagType: TagType,
    reason: String,
    startAt: LocalDateTime,
    endAt: LocalDateTime,
    current: LocalDateTime = DodamDate.now(),
    phoneReason: String? = null,
    onTrashClick: () -> Unit,
    playOnlyOnce: Boolean,
) {
    val nightStudyProgress = (
        ChronoUnit.SECONDS.between(
            startAt.toJavaLocalDateTime(),
            current.toJavaLocalDateTime(),
        ).toFloat() / ChronoUnit.SECONDS.between(
            startAt.toJavaLocalDateTime(),
            endAt.toJavaLocalDateTime(),
        )
        ).coerceIn(0f, 1f)

    val progress by animateFloatAsState(
        targetValue = if (playOnlyOnce) 0f else nightStudyProgress,
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 0,
            easing = FastOutLinearInEasing,
        ),
        label = "",
    )

    Surface(
        modifier = modifier,
        shape = DodamTheme.shapes.large,
        color = DodamTheme.colors.backgroundNormal,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = 16.dp,
                    horizontal = 12.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DodamTag(
                    text = if (tagType == TagType.Primary) "승인됨" else "대기 중",
                    tagType = tagType,
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            indication = rememberBounceIndication(),
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = onTrashClick,
                        ),
                    imageVector = DodamIcons.Trash.value,
                    contentDescription = "쓰레기통",
                    tint = DodamTheme.colors.lineNormal,
                )
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = reason,
                style = DodamTheme.typography.body1Medium(),
                color = DodamTheme.colors.labelNormal,
            )
            DodamDivider(type = DividerType.Normal)
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                val day = ChronoUnit.DAYS.between(
                    current.toJavaLocalDateTime(),
                    endAt.toJavaLocalDateTime(),
                ) + 1
                Text(
                    text = "${day}일",
                    style = DodamTheme.typography.heading2Bold(),
                    color = DodamTheme.colors.labelNormal,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "남음",
                    style = DodamTheme.typography.labelMedium(),
                    color = DodamTheme.colors.labelAlternative,
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                DodamLinerProgressIndicator(
                    progress = progress,
                    disabled = tagType != TagType.Primary,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "시작",
                        style = DodamTheme.typography.labelMedium(),
                        color = DodamTheme.colors.labelAlternative,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format(
                            "%d월 %d일",
                            startAt.monthNumber,
                            startAt.dayOfMonth,
                        ),
                        style = DodamTheme.typography.body1Medium(),
                        color = DodamTheme.colors.labelNeutral,
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "종료",
                        style = DodamTheme.typography.labelMedium(),
                        color = DodamTheme.colors.labelAlternative,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format(
                            "%d월 %d일",
                            endAt.monthNumber,
                            endAt.dayOfMonth,
                        ),
                        style = DodamTheme.typography.body1Medium(),
                        color = DodamTheme.colors.labelNeutral,
                    )
                }
            }

            if (phoneReason != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "휴대폰 사유",
                        style = DodamTheme.typography.labelMedium(),
                        color = DodamTheme.colors.labelAlternative,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = phoneReason,
                        style = DodamTheme.typography.body1Medium(),
                        color = DodamTheme.colors.labelNeutral,
                    )
                }
            }
        }
    }
}

@Composable
private fun NightStudyApplyRejectCell(modifier: Modifier = Modifier, reason: String, rejectReason: String, onTrashClick: () -> Unit) {
    Surface(
        modifier = modifier,
        shape = DodamTheme.shapes.large,
        color = DodamTheme.colors.backgroundNormal,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = 16.dp,
                    horizontal = 12.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DodamTag(
                    text = "거절됨",
                    tagType = TagType.Negative,
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            indication = rememberBounceIndication(),
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = onTrashClick,
                        ),
                    imageVector = DodamIcons.Trash.value,
                    contentDescription = "쓰레기통",
                    tint = DodamTheme.colors.lineNormal,
                )
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = reason,
                style = DodamTheme.typography.body1Medium(),
                color = DodamTheme.colors.labelNormal,
            )
            DodamDivider(type = DividerType.Normal)

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "거절 사유",
                    style = DodamTheme.typography.labelMedium(),
                    color = DodamTheme.colors.labelAlternative,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = rejectReason,
                    style = DodamTheme.typography.body1Medium(),
                    color = DodamTheme.colors.labelNeutral,
                )
            }
        }
    }
}
