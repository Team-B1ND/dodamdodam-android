package com.b1nd.dodam.student.home.card

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.data.core.model.Status
import com.b1nd.dodam.data.outing.model.OutType
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.DodamLinerProgressIndicator
import com.b1nd.dodam.designsystem.component.DodamLoadingDots
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.student.home.DefaultText
import com.b1nd.dodam.student.home.DodamContainer
import com.b1nd.dodam.student.home.model.OutUiState
import com.b1nd.dodam.ui.effect.shimmerEffect
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlinx.datetime.toJavaLocalDateTime

@Composable
internal fun OutCard(
    modifier: Modifier = Modifier,
    uiState: OutUiState,
    showShimmer: Boolean,
    navigateToOut: () -> Unit,
    navigateToOutApply: () -> Unit,
    fetchOut: () -> Unit,
) {
    val current = LocalDateTime.now()

    var playOnlyOnce by rememberSaveable { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(false) }

    DodamContainer(
        modifier = modifier,
        icon = DodamIcons.DoorOpen.value,
        title = "외출 외박",
        content = {
            if (!showShimmer) {
                when (uiState) {
                    is OutUiState.Success -> {
                        uiState.data?.let { out ->
                            val outProgress = (
                                1 - ChronoUnit.SECONDS.between(
                                    out.startAt.toJavaLocalDateTime(),
                                    current,
                                ).toFloat() / ChronoUnit.SECONDS.between(
                                    out.startAt.toJavaLocalDateTime(),
                                    out.endAt.toJavaLocalDateTime(),
                                )
                                )

                            val progress by animateFloatAsState(
                                targetValue = if (playOnlyOnce || isRefreshing) 0f else outProgress,
                                animationSpec = tween(
                                    durationMillis = 500,
                                    delayMillis = 0,
                                    easing = FastOutLinearInEasing,
                                ),
                                label = "",
                            )

                            LaunchedEffect(Unit) {
                                playOnlyOnce = false
                                isRefreshing = false
                            }

                            when (out.status) {
                                Status.ALLOWED -> {
                                    if (outProgress < 0) {
                                        DefaultText(
                                            onClick = navigateToOutApply,
                                            label = "외출, 외박이 필요하다면",
                                            body = "외출/외박 신청하기",
                                        )
                                    } else {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 10.dp)
                                                .clickable(
                                                    indication = rememberBounceIndication(),
                                                    interactionSource = remember { MutableInteractionSource() },
                                                    onClick = navigateToOut,
                                                )
                                                .padding(6.dp),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        ) {
                                            Column {
                                                Text(
                                                    text = buildAnnotatedString {
                                                        val totalMinutes = ChronoUnit.MINUTES.between(current, out.endAt.toJavaLocalDateTime())
                                                        val day = totalMinutes / (24 * 60)
                                                        val hour = (totalMinutes % (24 * 60)) / 60
                                                        val minute = totalMinutes % 60


                                                        when (out.outType) {
                                                            OutType.OUTING -> {
                                                                append(
                                                                    if (hour > 0) {
                                                                        "${hour}시간 "
                                                                    } else {
                                                                        "${minute}분 "
                                                                    },
                                                                )
                                                            }

                                                            OutType.SLEEPOVER -> {
                                                                append(
                                                                    if (day > 0) {
                                                                        "${day}일 "
                                                                    } else if (hour > 0) {
                                                                        "${hour}시간 "
                                                                    } else {
                                                                        "${minute}분 "
                                                                    },
                                                                )

                                                            }
                                                        }
                                                        withStyle(
                                                            style = DodamTheme.typography.labelMedium().copy(
                                                                color = DodamTheme.colors.labelAlternative,
                                                            ).toSpanStyle(),
                                                        ) {
                                                            append("남음")
                                                        }
                                                    },
                                                    style = DodamTheme.typography.heading2Bold(),
                                                    color = DodamTheme.colors.labelNormal,
                                                )

                                                DodamLinerProgressIndicator(progress = progress.coerceIn(0f, 1f))
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = when (out.outType) {
                                                        OutType.OUTING -> String.format(
                                                            "%02d:%02d 복귀",
                                                            out.endAt.hour,
                                                            out.endAt.minute,
                                                        )

                                                        OutType.SLEEPOVER -> String.format(
                                                            "%02d.%02d 까지",
                                                            out.endAt.monthNumber,
                                                            out.endAt.dayOfMonth,
                                                        )
                                                    },
                                                    color = DodamTheme.colors.labelAlternative,
                                                    style = DodamTheme.typography.labelRegular(),
                                                )
                                            }
                                        }
                                    }
                                }

                                Status.REJECTED -> {
                                    DefaultText(
                                        onClick = navigateToOutApply,
                                        label =
                                        "${if (out.outType == OutType.OUTING) "외출" else "외박"}이 거절되었어요",
                                        body = "다시 신청하기",
                                    )
                                }

                                Status.PENDING -> {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp)
                                            .clickable(
                                                onClick = navigateToOut,
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = rememberBounceIndication(),
                                            )
                                            .padding(6.dp),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    ) {
                                        Column {
                                            Text(
                                                text = "대기중",
                                                color = DodamTheme.colors.labelNormal,
                                                style = DodamTheme.typography.heading2Bold(),
                                            )
                                            Spacer(modifier = Modifier.height(12.dp))
                                            DodamLinerProgressIndicator(
                                                modifier = Modifier.fillMaxWidth(),
                                                progress = progress.coerceIn(0f, 1f),
                                                disabled = true,
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = if (out.outType == OutType.OUTING) {
                                                    String.format(
                                                        "%02d:%02d 복귀",
                                                        out.endAt.hour,
                                                        out.endAt.minute,
                                                    )
                                                } else {
                                                    String.format(
                                                        "%02d.%02d 까지",
                                                        out.endAt.monthNumber,
                                                        out.endAt.dayOfMonth,
                                                    )
                                                },
                                                color = DodamTheme.colors.labelAlternative,
                                                style = DodamTheme.typography.labelRegular(),
                                            )
                                        }
                                    }
                                }
                            }
                        } ?: run {
                            DefaultText(
                                onClick = navigateToOutApply,
                                label = "외출, 외박이 필요하다면",
                                body = "외출/외박 신청하기",
                            )
                        }
                    }

                    is OutUiState.Loading -> {
                        isRefreshing = true
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            DodamLoadingDots()
                        }
                    }

                    is OutUiState.Error -> {
                        DefaultText(
                            onClick = fetchOut,
                            label = "외출, 외박을 불러올 수 없어요",
                            body = "다시 불러오기",
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(28.dp)
                            .background(
                                shimmerEffect(),
                                CircleShape,
                            ),
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .background(
                                shimmerEffect(),
                                CircleShape,
                            ),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .width(91.dp)
                            .height(20.dp)
                            .background(
                                shimmerEffect(),
                                CircleShape,
                            ),
                    )
                }
            }
        },
    )
}
