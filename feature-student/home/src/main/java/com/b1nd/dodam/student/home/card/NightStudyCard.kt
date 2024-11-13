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
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.DodamLinerProgressIndicator
import com.b1nd.dodam.designsystem.component.DodamLoadingDots
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.student.home.DefaultText
import com.b1nd.dodam.student.home.DodamContainer
import com.b1nd.dodam.student.home.model.NightStudyUiState
import com.b1nd.dodam.ui.effect.shimmerEffect
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlinx.datetime.toJavaLocalDateTime

@Composable
internal fun NightStudyCard(
    modifier: Modifier = Modifier,
    uiState: NightStudyUiState,
    showShimmer: Boolean,
    navigateToAskNightStudy: () -> Unit,
    navigateToNightStudy: () -> Unit,
    fetchNightStudy: () -> Unit,
) {
    val current = LocalDateTime.now()

    var playOnlyOnce by rememberSaveable { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(false) }

    DodamContainer(
        modifier = modifier,
        icon = DodamIcons.MoonPlus.value,
        title = "심야 자습",
        content = {
            if (!showShimmer) {
                when (uiState) {
                    is NightStudyUiState.Success -> {
                        uiState.data?.let { nightStudy ->
                            val nightStudyProgress = 1 - ChronoUnit.SECONDS.between(
                                nightStudy.startAt.toJavaLocalDateTime(),
                                current,
                            ).toFloat() / ChronoUnit.SECONDS.between(
                                nightStudy.startAt.toJavaLocalDateTime(),
                                nightStudy.endAt.toJavaLocalDateTime(),
                            )

                            val progress by animateFloatAsState(
                                targetValue = if (playOnlyOnce || isRefreshing) 0f else nightStudyProgress,
                                animationSpec = tween(
                                    durationMillis = 300,
                                    delayMillis = 0,
                                    easing = FastOutLinearInEasing,
                                ),
                                label = "",
                            )

                            LaunchedEffect(Unit) {
                                playOnlyOnce = false
                                isRefreshing = false
                            }

                            when (nightStudy.status) {
                                Status.ALLOWED -> {
                                    if (nightStudyProgress < 0) {
                                        DefaultText(
                                            onClick = navigateToAskNightStudy,
                                            label = "공부할 시간이 필요하다면",
                                            body = "심야 자습 신청하기",
                                        )
                                    } else {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable(
                                                    indication = rememberBounceIndication(),
                                                    interactionSource = remember { MutableInteractionSource() },
                                                    onClick = navigateToNightStudy,
                                                )
                                                .padding(6.dp),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        ) {
                                            Column {
                                                Text(
                                                    text = buildAnnotatedString {
                                                        val day = ChronoUnit.DAYS.between(
                                                            current,
                                                            nightStudy.endAt.toJavaLocalDateTime(),
                                                        )
                                                        val hour = ChronoUnit.HOURS.between(
                                                            current,
                                                            nightStudy.endAt.toJavaLocalDateTime(),
                                                        )
                                                        val minute =
                                                            ChronoUnit.MINUTES.between(
                                                                current,
                                                                nightStudy.endAt.toJavaLocalDateTime(),
                                                            )

                                                        append(
                                                            if (day > 0) {
                                                                "${day}일 "
                                                            } else if (hour > 0) {
                                                                "${hour}시간 "
                                                            } else {
                                                                "${minute}분 "
                                                            },
                                                        )
                                                        withStyle(
                                                            style = DodamTheme.typography.labelMedium().toSpanStyle(),
                                                        ) {
                                                            append("남음")
                                                        }
                                                    },
                                                    style = DodamTheme.typography.heading2Bold(),
                                                    color = DodamTheme.colors.labelNormal,
                                                )
                                                Spacer(modifier = Modifier.height(12.dp))
                                                DodamLinerProgressIndicator(progress = progress)

                                                Spacer(modifier = Modifier.height(4.dp))

                                                Text(
                                                    text = String.format(
                                                        "%02d.%02d 까지",
                                                        nightStudy.endAt.monthNumber,
                                                        nightStudy.endAt.dayOfMonth,
                                                    ),
                                                    color = DodamTheme.colors.labelAlternative,
                                                    style = DodamTheme.typography.labelRegular(),
                                                )
                                            }
                                        }
                                    }
                                }

                                Status.REJECTED -> {
                                    DefaultText(
                                        onClick = navigateToAskNightStudy,
                                        label = "심야 자습이 거절되었어요",
                                        body = "다시 신청하기",
                                    )
                                }

                                Status.PENDING -> {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp)
                                            .clickable(
                                                indication = rememberBounceIndication(),
                                                interactionSource = remember { MutableInteractionSource() },
                                                onClick = navigateToNightStudy,
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

                                            DodamLinerProgressIndicator(
                                                modifier = Modifier.fillMaxWidth(),
                                                progress = progress,
                                                disabled = true,
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = String.format(
                                                    "%02d.%02d 까지",
                                                    nightStudy.endAt.monthNumber,
                                                    nightStudy.endAt.dayOfMonth,
                                                ),
                                                color = DodamTheme.colors.labelAlternative,
                                                style = DodamTheme.typography.labelRegular(),
                                            )
                                        }
                                    }
                                }
                            }
                        } ?: run {
                            DefaultText(
                                onClick = navigateToAskNightStudy,
                                label = "공부할 시간이 필요하다면",
                                body = "심야 자습 신청하기",
                            )
                        }
                    }

                    is NightStudyUiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            DodamLoadingDots()
                        }
                        isRefreshing = true
                    }

                    is NightStudyUiState.Error -> {
                        DefaultText(
                            onClick = fetchNightStudy,
                            label = "심야 자습을 불러올 수 없어요",
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
