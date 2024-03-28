package com.b1nd.dodam.student.home.card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.data.schedule.model.Grade
import com.b1nd.dodam.data.schedule.model.Schedule
import com.b1nd.dodam.dds.animation.LoadingDotsIndicator
import com.b1nd.dodam.dds.animation.bounceClick
import com.b1nd.dodam.dds.foundation.DodamIcons
import com.b1nd.dodam.dds.style.BodyMedium
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.student.home.DefaultText
import com.b1nd.dodam.student.home.DodamContainer
import com.b1nd.dodam.student.home.model.ScheduleUiState
import com.b1nd.dodam.ui.effect.shimmerEffect
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime

@ExperimentalFoundationApi
@Composable
internal fun ScheduleCard(
    uiState: ScheduleUiState,
    showShimmer: Boolean,
    fetchSchedule: () -> Unit,
    onContentClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    val current = LocalDateTime.now().toKotlinLocalDateTime().date
    val tomorrow = current.plus(DatePeriod(days = 1))

    DodamContainer(
        icon = DodamIcons.Calendar,
        title = "가까운 일정",
        showNextButton = true,
        onNextClick = onNextClick,
    ) {
        if (!showShimmer) {
            when (uiState) {
                is ScheduleUiState.Success -> {
                    val schedules = remember { uiState.data }
                    if (schedules.isEmpty()) {
                        DefaultText(
                            onClick = onContentClick,
                            label = "한 달간 일정이 없어요",
                            body = "전체 일정 확인하기",
                        )
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize()
                                .padding(horizontal = 10.dp)
                                .bounceClick(
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = onContentClick
                                )
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            val latestSchedule = remember {
                                if (current in schedules.first().date) {
                                    current
                                } else {
                                    if (tomorrow in schedules.first().date) {
                                        tomorrow
                                    } else {
                                        schedules.first().date.first()
                                    }
                                }
                            }

                            ScheduleComponent(
                                modifier = Modifier.weight(1f),
                                title = when {
                                    current == latestSchedule -> "오늘"

                                    tomorrow == latestSchedule -> "내일"

                                    else -> "D - ${latestSchedule.dayOfYear - current.dayOfYear}"
                                },
                                label = String.format(
                                    "%d월 %d일 (%s)",
                                    current.monthNumber,
                                    current.dayOfMonth,
                                    listOf(
                                        "월",
                                        "화",
                                        "수",
                                        "목",
                                        "금",
                                        "토",
                                        "일",
                                    )[current.dayOfWeek.value - 1],
                                ),
                                body = remember {
                                    schedules.filter {
                                        latestSchedule in it.date
                                    }
                                }
                            )

                            val nextSchedule = remember {
                                if (schedules.first().date.size > 1) {
                                    if (tomorrow in schedules.first().date) {
                                        tomorrow
                                    } else null
                                } else {
                                    if (schedules.size > 1) {
                                        schedules[2].date.first()
                                    } else null
                                }
                            }

                            nextSchedule?.let {
                                ScheduleComponent(
                                    modifier = Modifier.weight(1f),
                                    title = if (tomorrow == it) {
                                        "내일"
                                    } else {
                                        "D - ${it.dayOfYear - current.dayOfYear}"
                                    },
                                    label = String.format(
                                        "%d월 %d일 (%s)",
                                        it.monthNumber,
                                        it.dayOfMonth,
                                        listOf(
                                            "월",
                                            "화",
                                            "수",
                                            "목",
                                            "금",
                                            "토",
                                            "일",
                                        )[it.dayOfWeek.value - 1]
                                    ),
                                    body = remember {
                                        schedules.filter { schedule ->
                                            it in schedule.date
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                is ScheduleUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        LoadingDotsIndicator()
                    }
                }

                is ScheduleUiState.Error -> {
                    DefaultText(
                        onClick = fetchSchedule,
                        label = "일정을 불러올 수 없어요",
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
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(20.dp)
                            .background(
                                shimmerEffect(),
                                RoundedCornerShape(4.dp),
                            ),
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(14.dp)
                            .background(
                                shimmerEffect(),
                                RoundedCornerShape(4.dp),
                            ),
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(20.dp)
                            .background(
                                shimmerEffect(),
                                RoundedCornerShape(4.dp),
                            ),
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(20.dp)
                            .background(
                                shimmerEffect(),
                                RoundedCornerShape(4.dp),
                            ),
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(20.dp)
                            .background(
                                shimmerEffect(),
                                RoundedCornerShape(4.dp),
                            ),
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(14.dp)
                            .background(
                                shimmerEffect(),
                                RoundedCornerShape(4.dp),
                            ),
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(20.dp)
                            .background(
                                shimmerEffect(),
                                RoundedCornerShape(4.dp),
                            ),
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(20.dp)
                            .background(
                                shimmerEffect(),
                                RoundedCornerShape(4.dp),
                            ),
                    )
                }
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
private fun ScheduleComponent(
    modifier: Modifier = Modifier,
    title: String,
    label: String,
    body: List<Schedule>
) {
    Column(
        modifier = modifier,
    ) {
        BodyMedium(text = title, color = MaterialTheme.colorScheme.onSurface)
        LabelLarge(text = label, color = MaterialTheme.colorScheme.onSurfaceVariant)

        Spacer(modifier = Modifier.height(8.dp))

        body.forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            if (it.targetGrades.isEmpty()) {
                                Color(0xFF0167BC)
                            } else {
                                when (it.targetGrades.first()) {
                                    Grade.GRADE_1 -> Color(0xFFFCA800)
                                    Grade.GRADE_2 -> Color(0xFF3FBDE5)
                                    Grade.GRADE_3 -> Color(0xFFA252E1)
                                    Grade.GRADE_ALL -> Color(0xFFF97E6D)
                                    Grade.GRADE_ETC -> Color(0xFF0167BC)
                                }
                            },
                            CircleShape,
                        ),
                )

                Spacer(modifier = Modifier.width(4.dp))

                BodyMedium(
                    modifier = Modifier.basicMarquee(),
                    text = it.name,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
