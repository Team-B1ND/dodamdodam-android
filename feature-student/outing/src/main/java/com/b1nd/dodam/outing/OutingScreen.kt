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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.dds.animation.bounceCombinedClick
import com.b1nd.dodam.dds.component.DodamDialog
import com.b1nd.dodam.dds.component.DodamLinearProgressIndicator
import com.b1nd.dodam.dds.component.DodamToast
import com.b1nd.dodam.dds.component.DodamTopAppBar
import com.b1nd.dodam.dds.component.button.DodamIconButton
import com.b1nd.dodam.dds.component.button.DodamLargeFilledButton
import com.b1nd.dodam.dds.component.button.DodamSegment
import com.b1nd.dodam.dds.component.button.DodamSegmentedButtonRow
import com.b1nd.dodam.dds.foundation.DodamColor
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.BodyMedium
import com.b1nd.dodam.dds.style.CheckmarkCircleFilledIcon
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.dds.style.PlusIcon
import com.b1nd.dodam.outing.viewmodel.Event
import com.b1nd.dodam.outing.viewmodel.OutingViewModel
import com.b1nd.dodam.ui.component.DodamCard
import com.b1nd.dodam.ui.icons.ConvenienceStore
import com.b1nd.dodam.ui.icons.Tent
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlinx.datetime.toJavaLocalDateTime

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun OutingScreen(onAddOutingClick: () -> Unit, viewModel: OutingViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    var playOnlyOnceOuting by rememberSaveable { mutableStateOf(true) }
    var playOnlyOnceSleepover by rememberSaveable { mutableStateOf(true) }

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    val scrollState = rememberLazyListState()
    val current = LocalDateTime.now()

    val snackbarHostState = remember { SnackbarHostState() }

    var showDialog by remember { mutableStateOf(false) }
    var reason by remember { mutableStateOf("") }
    var id by remember { mutableLongStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.getMyOuting()
    }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect {
            when (it) {
                is Event.Error -> {
                }

                is Event.ShowToast -> {
                    showDialog = false
                    snackbarHostState.showSnackbar(
                        message = if (selectedIndex == 0) "외출을 삭제했어요" else "외박을 삭제했어요",
                    )
                }
            }
        }
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
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError,
                    ),
                    isLoading = uiState.isLoading,
                ) {
                    Text(text = "삭제")
                }
            },
            dismissButton = {
                DodamLargeFilledButton(
                    modifier = Modifier.weight(1f),
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
                    enabled = !uiState.isLoading,
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
                        .background(MaterialTheme.colorScheme.surface)
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
                AnimatedVisibility(scrollState.canScrollBackward) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.outlineVariant),
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                SnackbarHost(hostState = snackbarHostState) {
                    Column {
                        DodamToast(
                            text = it.visuals.message,
                            trailingIcon = {
                                CheckmarkCircleFilledIcon(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .drawBehind {
                                            drawRoundRect(
                                                color = DodamColor.White,
                                                topLeft = Offset(15f, 15f),
                                                size = Size(25f, 25f),
                                            )
                                        },
                                )
                            },
                        )
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
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

            if (selectedIndex == 0) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = scrollState,
                ) {
                    if (uiState.outings.isEmpty()) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.surfaceContainer,
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
                                    text = "아직 신청한 외출이 없어요.",
                                    color = MaterialTheme.colorScheme.tertiary,
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                DodamLargeFilledButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = onAddOutingClick,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    ),
                                ) {
                                    Text(text = "외출 신청하기")
                                }
                            }
                        }
                    } else {
                        items(
                            items = uiState.outings,
                            key = { it.id },
                        ) { out ->
                            val outingProgress = ChronoUnit.SECONDS.between(
                                out.startAt.toJavaLocalDateTime(),
                                current,
                            ).toFloat() / ChronoUnit.SECONDS.between(
                                out.startAt.toJavaLocalDateTime(),
                                out.endAt.toJavaLocalDateTime(),
                            )

                            val progress by animateFloatAsState(
                                targetValue = if (playOnlyOnceOuting) 0f else outingProgress,
                                animationSpec = tween(
                                    durationMillis = 500,
                                    delayMillis = 0,
                                    easing = FastOutLinearInEasing,
                                ),
                                label = "",
                            )

                            LaunchedEffect(Unit) {
                                playOnlyOnceOuting = false
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
                                    out.modifiedAt.monthNumber,
                                    out.modifiedAt.dayOfMonth,
                                    listOf(
                                        "월",
                                        "화",
                                        "수",
                                        "목",
                                        "금",
                                        "토",
                                        "일",
                                    )[out.modifiedAt.dayOfWeek.value - 1],
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
                                        .background(MaterialTheme.colorScheme.outlineVariant),
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
                                            color = MaterialTheme.colorScheme.onSurface,
                                        )
                                        LabelLarge(
                                            text = "외출",
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )

                                        Spacer(modifier = Modifier.weight(1f))

                                        BodyLarge(
                                            modifier = Modifier.padding(end = 4.dp),
                                            text = String.format(
                                                "%d시 %d분",
                                                out.endAt.hour,
                                                out.endAt.minute,
                                            ),
                                            color = MaterialTheme.colorScheme.onSurface,
                                        )
                                        LabelLarge(
                                            text = "복귀",
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )
                                    }

                                    if (out.status != Status.REJECTED) {
                                        DodamLinearProgressIndicator(
                                            modifier = Modifier.fillMaxWidth(),
                                            progress = progress,
                                            color = if (out.status == Status.ALLOWED) {
                                                MaterialTheme.colorScheme.primary
                                            } else {
                                                MaterialTheme.colorScheme.onSurfaceVariant
                                            },
                                        )
                                    }
                                    else {
                                        if (out.rejectReason != null) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                LabelLarge(
                                                    text = "거절 사유",
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )

                                                BodyMedium(
                                                    text = out.rejectReason!!,
                                                    color = MaterialTheme.colorScheme.onSurface,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = scrollState,
                ) {
                    if (uiState.sleepovers.isEmpty()) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        MaterialTheme.colorScheme.surfaceContainer,
                                        MaterialTheme.shapes.large,
                                    )
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Image(
                                    modifier = Modifier.size(36.dp),
                                    imageVector = if (selectedIndex == 0) ConvenienceStore else Tent,
                                    contentDescription = null,
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                LabelLarge(
                                    text = "아직 신청한 외박이 없어요.",
                                    color = MaterialTheme.colorScheme.tertiary,
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                DodamLargeFilledButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = onAddOutingClick,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    ),
                                ) {
                                    Text(text = "외박 신청하기")
                                }
                            }
                        }
                    } else {
                        items(
                            items = uiState.sleepovers,
                            key = { it.id },
                        ) { out ->
                            val outingProgress = ChronoUnit.SECONDS.between(
                                out.startAt.toJavaLocalDateTime(),
                                current,
                            ).toFloat() / ChronoUnit.SECONDS.between(
                                out.startAt.toJavaLocalDateTime(),
                                out.endAt.toJavaLocalDateTime(),
                            )

                            val progress by animateFloatAsState(
                                targetValue = if (playOnlyOnceSleepover) 0f else outingProgress,
                                animationSpec = tween(
                                    durationMillis = 500,
                                    delayMillis = 0,
                                    easing = FastOutLinearInEasing,
                                ),
                                label = "",
                            )

                            LaunchedEffect(Unit) {
                                playOnlyOnceSleepover = false
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
                                    out.modifiedAt.monthNumber,
                                    out.modifiedAt.dayOfMonth,
                                    listOf(
                                        "월",
                                        "화",
                                        "수",
                                        "목",
                                        "금",
                                        "토",
                                        "일",
                                    )[out.modifiedAt.dayOfWeek.value - 1],
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
                                        .background(MaterialTheme.colorScheme.outlineVariant),
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
                                                "%d월 %d일",
                                                out.startAt.monthNumber,
                                                out.startAt.dayOfMonth,
                                            ),
                                            color = MaterialTheme.colorScheme.onSurface,
                                        )
                                        LabelLarge(
                                            text = "외박",
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )

                                        Spacer(modifier = Modifier.weight(1f))

                                        BodyLarge(
                                            modifier = Modifier.padding(end = 4.dp),
                                            text = String.format(
                                                "%d월 %d일",
                                                out.endAt.monthNumber,
                                                out.endAt.dayOfMonth,
                                            ),
                                            color = MaterialTheme.colorScheme.onSurface,
                                        )
                                        LabelLarge(
                                            text = "복귀",
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )
                                    }

                                    if (out.status != Status.REJECTED) {
                                        DodamLinearProgressIndicator(
                                            modifier = Modifier.fillMaxWidth(),
                                            progress = progress,
                                            color = if (out.status == Status.ALLOWED) {
                                                MaterialTheme.colorScheme.primary
                                            } else {
                                                MaterialTheme.colorScheme.onSurfaceVariant
                                            },
                                        )
                                    }
                                    else {
                                        if (out.rejectReason != null) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                LabelLarge(
                                                    text = "거절 사유",
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )

                                                BodyMedium(
                                                    text = out.rejectReason!!,
                                                    color = MaterialTheme.colorScheme.onSurface,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            }
                                        }
                                    }
                                }
                            }
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
