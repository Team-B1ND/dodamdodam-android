package com.b1nd.dodam.nightstudy

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.dds.animation.bounceCombinedClick
import com.b1nd.dodam.dds.component.DodamDialog
import com.b1nd.dodam.dds.component.DodamLinearProgressIndicator
import com.b1nd.dodam.dds.component.DodamTopAppBar
import com.b1nd.dodam.dds.component.button.DodamIconButton
import com.b1nd.dodam.dds.component.button.DodamLargeFilledButton
import com.b1nd.dodam.dds.foundation.DodamIcons
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.BodyMedium
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.dds.style.MoonPlusIcon
import com.b1nd.dodam.dds.style.PlusIcon
import com.b1nd.dodam.nightstudy.viewmodel.NightStudyUiState
import com.b1nd.dodam.nightstudy.viewmodel.NightStudyViewModel
import com.b1nd.dodam.ui.component.DodamCard
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.b1nd.dodam.ui.icons.ConvenienceStore
import com.b1nd.dodam.ui.icons.SmileMoon

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun NightStudyScreen(
    onAddClick: () -> Unit,
    showToast: (String, String) -> Unit,
    refresh: () -> Boolean,
    dispose: () -> Unit,
    viewModel: NightStudyViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val nightStudyScreenState = rememberNightStudyScreenState()

    var playOnlyOnce by rememberSaveable { mutableStateOf(true) }

    var showDialog by remember { mutableStateOf(false) }
    var reason by remember { mutableStateOf("") }
    var id by remember { mutableLongStateOf(0) }

    val color = MaterialTheme.colorScheme

    DisposableEffect(Unit) {
        if (refresh()) viewModel.getMyNigthStudy()

        onDispose(dispose)
    }

    if (showDialog) {
        DodamDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                DodamLargeFilledButton(
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.deleteNightStudy(id) },
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
                        Text(text = "심야 자습")
                    },
                    actions = {
                        DodamIconButton(onClick = onAddClick) {
                            PlusIcon(modifier = Modifier.size(28.dp))
                        }
                    },
                )
                AnimatedVisibility(nightStudyScreenState.canScrollBackward) {
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
                                val outingProgress = nightStudyScreenState.getProgress(nightStudy.startAt, nightStudy.endAt)

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
                                                id = nightStudy.id
                                                reason = nightStudy.content
                                                showDialog = true
                                            },
                                            interactionColor = Color.Transparent,
                                        ),
                                    statusText = when (nightStudy.status) {
                                        Status.ALLOWED -> "승인됨"
                                        Status.REJECTED -> "거절됨"
                                        Status.PENDING -> "대기중"
                                    },
                                    statusColor = when (nightStudy.status) {
                                        Status.ALLOWED -> MaterialTheme.colorScheme.primary
                                        Status.REJECTED -> MaterialTheme.colorScheme.error
                                        Status.PENDING -> MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                                    labelText = String.format(
                                        "%d월 %d일 (%s)",
                                        nightStudy.startAt.monthNumber,
                                        nightStudy.startAt.dayOfMonth,
                                        listOf(
                                            "월",
                                            "화",
                                            "수",
                                            "목",
                                            "금",
                                            "토",
                                            "일",
                                        )[nightStudy.startAt.dayOfWeek.value - 1],
                                    ),
                                ) {
                                    BodyMedium(
                                        text = nightStudy.content,
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
                                                    "%d월 %d일",
                                                    nightStudy.startAt.monthNumber,
                                                    nightStudy.startAt.dayOfMonth,
                                                ),
                                                color = color.onSurface,
                                            )
                                            LabelLarge(
                                                text = "시작",
                                                color = color.onSurfaceVariant,
                                            )

                                            Spacer(modifier = Modifier.weight(1f))

                                            BodyLarge(
                                                modifier = Modifier.padding(end = 4.dp),
                                                text = String.format(
                                                    "%d월 %d일",
                                                    nightStudy.endAt.monthNumber,
                                                    nightStudy.endAt.dayOfMonth,
                                                ),
                                                color = color.onSurface,
                                            )
                                            LabelLarge(
                                                text = "종료",
                                                color = color.onSurfaceVariant,
                                            )
                                        }

                                        if (nightStudy.status != Status.REJECTED) {
                                            DodamLinearProgressIndicator(
                                                modifier = Modifier.fillMaxWidth(),
                                                progress = progress,
                                                color = if (nightStudy.status == Status.ALLOWED) {
                                                    color.primary
                                                } else {
                                                    color.onSurfaceVariant
                                                },
                                            )

                                            if (nightStudy.doNeedPhone) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                ) {
                                                    LabelLarge(
                                                        text = "휴대폰 사유",
                                                        color = color.onSurfaceVariant,
                                                    )

                                                    BodyMedium(
                                                        text = nightStudy.reasonForPhone!!,
                                                        color = color.onSurface,
                                                        fontWeight = FontWeight.Medium,
                                                    )
                                                }
                                            }
                                        } else {
                                            if (nightStudy.rejectReason != null) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                ) {
                                                    LabelLarge(
                                                        text = "거절 사유",
                                                        color = color.onSurfaceVariant,
                                                    )

                                                    BodyMedium(
                                                        text = nightStudy.rejectReason!!,
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
                                        imageVector = SmileMoon,
                                        contentDescription = null,
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    LabelLarge(
                                        text = "아직 신청한 심야 자습이 없어요.",
                                        color = color.tertiary,
                                    )

                                    Spacer(modifier = Modifier.height(24.dp))

                                    DodamLargeFilledButton(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = onAddClick,
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = color.secondaryContainer,
                                            contentColor = color.onSecondaryContainer,
                                        ),
                                    ) {
                                        Text(text = "심야 자습 신청하기")
                                    }
                                }
                            }
                        }
                    }

                    is NightStudyUiState.Loading -> {
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
