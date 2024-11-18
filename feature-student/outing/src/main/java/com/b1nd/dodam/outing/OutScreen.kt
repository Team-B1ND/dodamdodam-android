package com.b1nd.dodam.outing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.outing.model.OutType
import com.b1nd.dodam.data.outing.model.Outing
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
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTag
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TagType
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.outing.viewmodel.OutUiState
import com.b1nd.dodam.outing.viewmodel.OutingViewModel
import com.b1nd.dodam.ui.component.DodamCard
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.b1nd.dodam.ui.icons.ConvenienceStore
import com.b1nd.dodam.ui.icons.Tent
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import org.koin.androidx.compose.koinViewModel
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun OutingScreen(
    onAddOutingClick: () -> Unit,
    showToast: (String, String) -> Unit,
    refresh: () -> Boolean,
    dispose: () -> Unit,
    viewModel: OutingViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val outScreenState = rememberOutScreenState()

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var reason by remember { mutableStateOf("") }
    var id by remember { mutableLongStateOf(0) }
    var playOnlyOnce by rememberSaveable { mutableStateOf(true) }

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = false
            viewModel.getMyOuting()
        },
    )

    DisposableEffect(Unit) {
        if (refresh()) viewModel.getMyOuting()

        onDispose(dispose)
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
            }
        ) {
            DodamButtonDialog(
                confirmButtonText = "삭제",
                confirmButtonRole = ButtonRole.Negative,
                confirmButton = {
                    if (selectedIndex == 0) {
                        viewModel.deleteOuting(id)
                    } else {
                        viewModel.deleteSleepover(id)
                    }
                },
                dismissButtonText = "취소",
                dismissButtonRole = ButtonRole.Primary,
                dismissButton = {
                    showDialog = false
                },
                title = "정말 삭제하시겠어요?",
                body = reason
            )
        }
    }

    Scaffold(
        topBar = {
            Column {
                DodamDefaultTopAppBar(
                    title = "외출/외박",
                    actionIcons = persistentListOf(
                        ActionIcon(
                            icon = DodamIcons.Plus,
                            onClick = onAddOutingClick,
                        )
                    ),
                )
                AnimatedVisibility(outScreenState.canScrollBackward) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(DodamTheme.colors.fillNeutral),
                    )
                }
            }
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
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
                DodamSegmentedButton(
                    segments = List(2) { index ->
                        DodamSegment(
                            selected = index == selectedIndex,
                            onClick = { selectedIndex = index },
                            text = if (index == 0) "외출" else "외박"
                        )
                    }.toImmutableList()
                )

                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = outScreenState.lazyListState,
                ) {
                    when (val outUiState = uiState) {
                        is OutUiState.Success -> {
                            if (if (selectedIndex == 0) outUiState.outings.isNotEmpty() else outUiState.sleepovers.isNotEmpty()) {
                                items(
                                    items = if (selectedIndex == 0) outUiState.outings else outUiState.sleepovers,
                                    key = { it.id },
                                ) { out ->
                                    LaunchedEffect(Unit) {
                                        playOnlyOnce = false
                                    }

                                    when (out.status) {
                                        Status.PENDING -> {
                                            OutApplyCell(
                                                tagType = TagType.Secondary,
                                                reason = out.reason,
                                                startAt = out.startAt,
                                                endAt = out.endAt,
                                                onTrashClick = {
                                                    id = out.id
                                                    reason = out.reason
                                                    showDialog = true
                                                },
                                                playOnlyOnce = playOnlyOnce,
                                                isOut = out.outType == OutType.OUTING
                                            )
                                        }
                                        Status.ALLOWED -> {
                                            OutApplyCell(
                                                tagType = TagType.Primary,
                                                reason = out.reason,
                                                startAt = out.startAt,
                                                endAt = out.endAt,
                                                onTrashClick = {
                                                    id = out.id
                                                    reason = out.reason
                                                    showDialog = true
                                                },
                                                playOnlyOnce = playOnlyOnce,
                                                isOut = out.outType == OutType.OUTING,
                                            )
                                        }
                                        Status.REJECTED -> {
                                            OutApplyRejectCell(
                                                reason = out.reason,
                                                rejectReason = out.rejectReason?: "",
                                                onTrashClick = {
                                                    id = out.id
                                                    reason = out.reason
                                                    showDialog = true
                                                }
                                            )
                                        }
                                    }
                                }
                            } else {
                                item {
                                    DodamEmpty(
                                        onClick = onAddOutingClick,
                                        title = if (selectedIndex == 0) "아직 신청한 외출이 없어요." else "아직 신청한 외박이 없어요.",
                                        imageVector = if (selectedIndex == 0) ConvenienceStore else Tent,
                                        buttonText = (if (selectedIndex == 0) "외출" else "외박") + " 신청하기",
                                    )
                                }
                            }
                        }

                        is OutUiState.Loading -> {
                            item {
                                Surface(
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

                        is OutUiState.Error -> showToast(
                            "ERROR",
                            (if (selectedIndex == 0) "외출" else "외박") + "을 불러올 수 없어요",
                        )

                        is OutUiState.SuccessDelete -> {
                            showDialog = false
                            showToast(
                                "SUCCESS",
                                (if (selectedIndex == 0) "외출" else "외박") + "을 삭제했어요",
                            )
                        }

                        is OutUiState.FailDelete -> {
                            showDialog = false
                            showToast(
                                "ERROR",
                                (if (selectedIndex == 0) "외출" else "외박") + "삭제를 실패했어요",
                            )
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
private fun OutApplyCell(
    modifier: Modifier = Modifier,
    tagType: TagType,
    reason: String,
    startAt: LocalDateTime,
    endAt: LocalDateTime,
    isOut: Boolean,
    current: LocalDateTime = DodamDate.now(),
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
                val totalMinutes = ChronoUnit.MINUTES.between(
                    current.toJavaLocalDateTime(),
                    endAt.toJavaLocalDateTime()
                )
                val day = totalMinutes / (24 * 60)
                val hour = (totalMinutes % (24 * 60)) / 60
                val minute = totalMinutes % 60

                val text = if (isOut)
                    if (hour > 0) {
                        "${hour}시간 "
                    } else {
                        "${minute}분 "
                    } else if (day > 0) {
                        "${day+1}일 "
                    } else if (hour > 0) {
                        "${hour}시간 "
                    } else {
                        "${minute}분 "
                    }
                Text(
                    text = text,
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
                        text = if (isOut) "외출" else "외박",
                        style = DodamTheme.typography.labelMedium(),
                        color = DodamTheme.colors.labelAlternative,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isOut)
                            String.format(
                                "%d시 %d분",
                                startAt.hour,
                                startAt.minute,
                            ) else String.format(
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
                        text = if (isOut)
                            String.format(
                                "%d시 %d분",
                                endAt.hour,
                                endAt.minute,
                            ) else
                            String.format(
                                "%d월 %d일",
                                endAt.monthNumber,
                                endAt.dayOfMonth,
                            ),
                        style = DodamTheme.typography.body1Medium(),
                        color = DodamTheme.colors.labelNeutral,
                    )
                }
            }
        }
    }
}

@Composable
private fun OutApplyRejectCell(modifier: Modifier = Modifier, reason: String, rejectReason: String, onTrashClick: () -> Unit) {
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
