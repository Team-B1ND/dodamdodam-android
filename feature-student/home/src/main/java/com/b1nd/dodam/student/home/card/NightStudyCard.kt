package com.b1nd.dodam.student.home.card

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import com.b1nd.dodam.dds.animation.LoadingDotsIndicator
import com.b1nd.dodam.dds.animation.bounceClick
import com.b1nd.dodam.dds.component.DodamCircularProgressIndicator
import com.b1nd.dodam.dds.foundation.DodamIcons
import com.b1nd.dodam.dds.style.BodyMedium
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.student.home.DefaultText
import com.b1nd.dodam.student.home.DodamContainer
import com.b1nd.dodam.student.home.model.NightStudyUiState
import com.b1nd.dodam.ui.effect.shimmerEffect
import kotlinx.datetime.toJavaLocalDateTime
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

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
        icon = DodamIcons.MoonPlus,
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
                                                .padding(horizontal = 10.dp)
                                                .bounceClick(
                                                    interactionSource = remember { MutableInteractionSource() },
                                                    onClick = navigateToNightStudy
                                                )
                                                .padding(6.dp),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            DodamCircularProgressIndicator(progress = progress)
                                            Column(
                                                verticalArrangement = Arrangement.spacedBy(4.dp)
                                            ) {
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
                                                            style = MaterialTheme.typography.labelLarge.copy(
                                                                MaterialTheme.colorScheme.onSurfaceVariant,
                                                            ).toSpanStyle(),
                                                        ) {
                                                            append("남음")
                                                        }
                                                    },
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = MaterialTheme.colorScheme.onSurface,
                                                )

                                                LabelLarge(
                                                    text = String.format(
                                                        "%02d.%02d 까지",
                                                        nightStudy.endAt.monthNumber,
                                                        nightStudy.endAt.dayOfMonth,
                                                    ),
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
                                            .bounceClick(
                                                interactionSource = remember { MutableInteractionSource() },
                                                onClick = navigateToNightStudy
                                            )
                                            .padding(6.dp),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        DodamCircularProgressIndicator(progress = progress)
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            BodyMedium(
                                                text = "대기중",
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            LabelLarge(
                                                text = String.format(
                                                    "%02d.%02d 시작",
                                                    nightStudy.startAt.monthNumber,
                                                    nightStudy.startAt.dayOfMonth,
                                                ),
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
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
                            LoadingDotsIndicator()
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .background(
                                shimmerEffect(),
                                CircleShape,
                            ),
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier
                                    .weight(0.9f)
                                    .height(20.dp)
                                    .background(
                                        shimmerEffect(),
                                        RoundedCornerShape(4.dp),
                                    ),
                            )
                            Spacer(modifier = Modifier.weight(0.1f))
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .width(50.dp)
                                .height(15.dp)
                                .background(
                                    shimmerEffect(),
                                    RoundedCornerShape(4.dp),
                                ),
                        )
                    }
                }
            }
        }
    )
}
