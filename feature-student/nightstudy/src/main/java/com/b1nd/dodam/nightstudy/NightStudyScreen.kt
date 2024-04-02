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
import androidx.compose.foundation.rememberScrollState
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
import com.b1nd.dodam.dds.foundation.DodamColor
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.BodyMedium
import com.b1nd.dodam.dds.style.CheckmarkCircleFilledIcon
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.dds.style.PlusIcon
import com.b1nd.dodam.nightstudy.viewmodel.Event
import com.b1nd.dodam.nightstudy.viewmodel.NightStudyViewModel
import com.b1nd.dodam.ui.component.DodamCard
import com.b1nd.dodam.ui.icons.SmileMoon
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlinx.datetime.toJavaLocalDateTime

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun NightStudyScreen(onAddClick: () -> Unit, viewModel: NightStudyViewModel = hiltViewModel()) {
    val current = LocalDateTime.now()
    val uiState by viewModel.uiState.collectAsState()

    var playOnlyOnce by rememberSaveable { mutableStateOf(true) }

    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }

    var showDialog by remember { mutableStateOf(false) }
    var reason by remember { mutableStateOf("") }
    var id by remember { mutableLongStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.getMyNightStudy()
    }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect {
            when (it) {
                is Event.Error -> {
                }

                is Event.ShowToast -> {
                    showDialog = false
                    snackbarHostState.showSnackbar(
                        message = "심야 자습을 삭제했어요",
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
                    onClick = { viewModel.deleteNightStudy(id) },
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
                        Text(text = "심야 자습")
                    },
                    actions = {
                        DodamIconButton(onClick = onAddClick) {
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
                                                topLeft = Offset(12f, 12f),
                                                size = Size(30f, 30f),
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

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (uiState.nightStudy.isNotEmpty()) {
                    items(
                        items = uiState.nightStudy,
                        key = { it.id },
                    ) { nightStudy ->
                        val nightStudyProgress = ChronoUnit.SECONDS.between(
                            nightStudy.startAt.toJavaLocalDateTime(),
                            current,
                        ).toFloat() / ChronoUnit.SECONDS.between(
                            nightStudy.startAt.toJavaLocalDateTime(),
                            nightStudy.endAt.toJavaLocalDateTime(),
                        )

                        val progress by animateFloatAsState(
                            targetValue = if (playOnlyOnce) 0f else nightStudyProgress,
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
                                nightStudy.modifiedAt.monthNumber,
                                nightStudy.modifiedAt.dayOfMonth,
                                listOf(
                                    "월",
                                    "화",
                                    "수",
                                    "목",
                                    "금",
                                    "토",
                                    "일",
                                )[nightStudy.modifiedAt.dayOfWeek.value - 1],
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
                                    .background(MaterialTheme.colorScheme.outlineVariant),
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
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
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                    LabelLarge(
                                        text = "시작",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )

                                    Spacer(modifier = Modifier.weight(1f))

                                    BodyLarge(
                                        modifier = Modifier.padding(end = 4.dp),
                                        text = String.format(
                                            "%d월 %d일",
                                            nightStudy.endAt.monthNumber,
                                            nightStudy.endAt.dayOfMonth,
                                        ),
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                    LabelLarge(
                                        text = "복귀",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }

                                if (nightStudy.status != Status.REJECTED) {
                                    DodamLinearProgressIndicator(
                                        modifier = Modifier.fillMaxWidth(),
                                        progress = progress,
                                        color = if (nightStudy.status == Status.ALLOWED) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        },
                                    )
                                }

                                /* TODO : 거절 사유 만들어지면 주석 해제
                                    else {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            LabelLarge(
                                                text = "거절 사유",
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )

                                            BodyMedium(
                                                text = outing.,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                 */

                                if (nightStudy.doNeedPhone) {
                                    Row(
                                        modifier = Modifier.padding(top = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    ) {
                                        LabelLarge(
                                            text = "휴대폰 사유",
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )

                                        BodyMedium(
                                            text = nightStudy.reasonForPhone!!,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontWeight = FontWeight.Medium,
                                        )
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
                                    MaterialTheme.colorScheme.surfaceContainer,
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

                            LabelLarge(text = "아직 신청한 심야 자습이 없어요.", color = MaterialTheme.colorScheme.tertiary)

                            Spacer(modifier = Modifier.height(24.dp))

                            DodamLargeFilledButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = onAddClick,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                ),
                            ) {
                                Text(text = "심야 자습 신청하기")
                            }
                        }
                    }
                }
            }
        }
    }
}
