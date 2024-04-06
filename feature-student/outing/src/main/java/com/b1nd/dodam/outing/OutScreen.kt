package com.b1nd.dodam.outing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.dds.animation.bounceCombinedClick
import com.b1nd.dodam.dds.component.DodamDialog
import com.b1nd.dodam.dds.component.DodamLinearProgressIndicator
import com.b1nd.dodam.dds.component.DodamTopAppBar
import com.b1nd.dodam.dds.component.button.DodamIconButton
import com.b1nd.dodam.dds.component.button.DodamLargeFilledButton
import com.b1nd.dodam.dds.component.button.DodamSegment
import com.b1nd.dodam.dds.component.button.DodamSegmentedButtonRow
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.BodyMedium
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.dds.style.PlusIcon
import com.b1nd.dodam.outing.viewmodel.OutUiState
import com.b1nd.dodam.outing.viewmodel.OutingViewModel
import com.b1nd.dodam.ui.component.DodamCard
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.b1nd.dodam.ui.icons.ConvenienceStore
import kotlinx.datetime.toJavaLocalDateTime
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun OutingScreen(
    onAddOutingClick: () -> Unit,
    showToast: (String, String) -> Unit,
    refresh: () -> Boolean,
    dispose: () -> Unit,
    viewModel: OutingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val outScreenState = rememberOutScreenState()

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    var reason by remember { mutableStateOf("") }
    var id by remember { mutableLongStateOf(0) }
    var playOnlyOnce by rememberSaveable { mutableStateOf(true) }

    val color = MaterialTheme.colorScheme

    DisposableEffect(Unit) {
        if (refresh()) viewModel.getMyOuting()

        onDispose(dispose)
    }

    if (showDialog) {
        DodamDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                DodamLargeFilledButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (selectedIndex == 0) {
                            viewModel.deleteOuting(id)
                        } else {
                            viewModel.deleteSleepover(id)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = color.error,
                        contentColor = color.onError,
                    ),
                ) {
                    Text(text = "삭제")
                }
            },
            dismissButton = {
                DodamLargeFilledButton(
                    modifier = Modifier.weight(1f),
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = color.secondaryContainer,
                        contentColor = color.onSecondaryContainer,
                    ),
                ) {
                    Text(text = "취소")
                }
            },
            title = {
                Text(text = "정말 삭제하시겠어요?")
            },
            text = {
                Text(text = reason)
            },
        )
    }

    Scaffold(
        topBar = {
            Column {
                DodamTopAppBar(
                    modifier = Modifier
                        .background(color.surface)
                        .padding(horizontal = 4.dp),
                    title = {
                        Text(text = "외출/외박")
                    },
                    actions = {
                        DodamIconButton(onClick = onAddOutingClick) {
                            PlusIcon(modifier = Modifier.size(28.dp))
                        }
                    },
                )
                AnimatedVisibility(outScreenState.canScrollBackward) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color.outlineVariant),
                    )
                }
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color.surface)
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            DodamSegmentedButtonRow(selectedIndex = selectedIndex) {
                DodamSegment(selected = selectedIndex == 0, onClick = { selectedIndex = 0 }) {
                    Text(text = "외출")
                }

                DodamSegment(selected = selectedIndex == 1, onClick = { selectedIndex = 1 }) {
                    Text(text = "외박")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                                val outingProgress = outScreenState.getProgress(out.startAt, out.endAt)

                                val progress by animateFloatAsState(
                                    targetValue = if (playOnlyOnce) 0f else outingProgress,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        delayMillis = 0,
                                        easing = FastOutLinearInEasing,
                                    ),
                                    label = "",
                                )

                                LaunchedEffect(Unit) {
                                    playOnlyOnce = false
                                }

                                DodamCard(
                                    modifier = Modifier
                                        .bounceCombinedClick(
                                            onClick = {},
                                            onLongClick = {
                                                id = out.id
                                                reason = out.reason
                                                showDialog = true
                                            },
                                            interactionColor = Color.Transparent,
                                        ),
                                    statusText = when (out.status) {
                                        Status.ALLOWED -> "승인됨"
                                        Status.REJECTED -> "거절됨"
                                        Status.PENDING -> "대기중"
                                    },
                                    statusColor = when (out.status) {
                                        Status.ALLOWED -> MaterialTheme.colorScheme.primary
                                        Status.REJECTED -> MaterialTheme.colorScheme.error
                                        Status.PENDING -> MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                                    labelText = String.format(
                                        "%d월 %d일 (%s)",
                                        out.startAt.monthNumber,
                                        out.startAt.dayOfMonth,
                                        listOf(
                                            "월",
                                            "화",
                                            "수",
                                            "목",
                                            "금",
                                            "토",
                                            "일",
                                        )[out.startAt.dayOfWeek.value - 1],
                                    ),
                                ) {
                                    BodyMedium(
                                        text = out.reason,
                                        fontWeight = FontWeight.Medium,
                                    )

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(color.outlineVariant),
                                    )

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(6.dp),
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.Bottom,
                                        ) {
                                            BodyLarge(
                                                modifier = Modifier.padding(end = 4.dp),
                                                text = String.format(
                                                    "%d시 %d분",
                                                    out.startAt.hour,
                                                    out.startAt.minute,
                                                ),
                                                color = color.onSurface,
                                            )
                                            LabelLarge(
                                                text = if (selectedIndex == 0) "외출" else "외박",
                                                color = color.onSurfaceVariant,
                                            )

                                            Spacer(modifier = Modifier.weight(1f))

                                            BodyLarge(
                                                modifier = Modifier.padding(end = 4.dp),
                                                text = String.format(
                                                    "%d시 %d분",
                                                    out.endAt.hour,
                                                    out.endAt.minute,
                                                ),
                                                color = color.onSurface,
                                            )
                                            LabelLarge(
                                                text = "복귀",
                                                color = color.onSurfaceVariant,
                                            )
                                        }

                                        if (out.status != Status.REJECTED) {
                                            DodamLinearProgressIndicator(
                                                modifier = Modifier.fillMaxWidth(),
                                                progress = progress,
                                                color = if (out.status == Status.ALLOWED) {
                                                    color.primary
                                                } else {
                                                    color.onSurfaceVariant
                                                },
                                            )
                                        } else {
                                            if (out.rejectReason != null) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                ) {
                                                    LabelLarge(
                                                        text = "거절 사유",
                                                        color = color.onSurfaceVariant,
                                                    )

                                                    BodyMedium(
                                                        text = out.rejectReason!!,
                                                        color = color.onSurface,
                                                        fontWeight = FontWeight.Medium,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color.surfaceContainer,
                                            MaterialTheme.shapes.large,
                                        )
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Image(
                                        modifier = Modifier.size(36.dp),
                                        imageVector = ConvenienceStore,
                                        contentDescription = null,
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    LabelLarge(
                                        text = if (selectedIndex == 0) "아직 신청한 외출이 없어요." else "아직 신청한 외박이 없어요.",
                                        color = color.tertiary,
                                    )

                                    Spacer(modifier = Modifier.height(24.dp))

                                    DodamLargeFilledButton(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = onAddOutingClick,
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = color.secondaryContainer,
                                            contentColor = color.onSecondaryContainer,
                                        ),
                                    ) {
                                        Text(text = if (selectedIndex == 0) "외출 신청하기" else "외박 신청하기")
                                    }
                                }
                            }
                        }
                    }

                    is OutUiState.Loading -> {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.surfaceContainer,
                                        MaterialTheme.shapes.large,
                                    )
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(52.dp, 27.dp)
                                            .background(shimmerEffect(), CircleShape),
                                    )

                                    Spacer(modifier = Modifier.weight(1f))

                                    Box(
                                        modifier = Modifier
                                            .size(52.dp, 20.dp)
                                            .background(shimmerEffect(), RoundedCornerShape(4.dp)),
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .height(20.dp)
                                        .fillMaxWidth()
                                        .background(shimmerEffect(), RoundedCornerShape(4.dp)),
                                )

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(52.dp, 27.dp)
                                            .background(shimmerEffect(), CircleShape),
                                    )

                                    Spacer(modifier = Modifier.weight(1f))

                                    Box(
                                        modifier = Modifier
                                            .size(52.dp, 20.dp)
                                            .background(shimmerEffect(), RoundedCornerShape(4.dp)),
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .height(20.dp)
                                        .fillMaxWidth()
                                        .background(shimmerEffect(), CircleShape),
                                )
                            }
                        }
                    }

                    is OutUiState.Error -> showToast(
                        "ERROR",
                        (if (selectedIndex == 0) "외출" else "외박") + "을 불러올 수 없어요"
                    )

                    is OutUiState.SuccessDelete -> {
                        showDialog = false
                        showToast(
                            "SUCCESS",
                            (if (selectedIndex == 0) "외출" else "외박") + "을 삭제했어요"
                        )
                    }

                    is OutUiState.FailDelete -> {
                        showDialog = false
                        showToast(
                            "ERROR",
                            (if (selectedIndex == 0) "외출" else "외박") + "삭제를 실패했어요"
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
