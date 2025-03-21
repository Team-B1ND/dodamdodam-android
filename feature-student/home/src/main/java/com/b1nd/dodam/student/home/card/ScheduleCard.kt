package com.b1nd.dodam.student.home.card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import com.b1nd.dodam.data.schedule.model.Grade
import com.b1nd.dodam.data.schedule.model.Schedule
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.DodamLoadingDots
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.student.home.DefaultText
import com.b1nd.dodam.student.home.DodamContainer
import com.b1nd.dodam.student.home.model.ScheduleUiState
import com.b1nd.dodam.ui.effect.shimmerEffect
import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus
import kotlinx.datetime.toKotlinLocalDateTime

@ExperimentalFoundationApi
@Composable
fun ScheduleCard(uiState: ScheduleUiState, showShimmer: Boolean, fetchSchedule: () -> Unit, onContentClick: () -> Unit, onNextClick: () -> Unit) {
    val current = LocalDateTime.now().toKotlinLocalDateTime().date
    val tomorrow = current.plus(DatePeriod(days = 1))

    DodamContainer(
        icon = DodamIcons.Calendar.value,
        title = "가까운 일정",
    ) {
        if (!showShimmer) {
            when (uiState) {
                is ScheduleUiState.Success -> {
                    val schedules = remember { uiState.data }
                    if (schedules.isEmpty()) {
                        DefaultText(
                            onClick = onContentClick,
                            label = "다음 달 일정을 기다려주세요",
                            body = "한 달간 일정이 없어요",
                        )
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize()
                                .padding(horizontal = 10.dp)
                                .clickable(
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    },
                                    indication = rememberBounceIndication(),
                                    onClick = onContentClick,
                                )
                                .padding(6.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
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
                                day = "${
                                    if (current == latestSchedule) {
                                        current.dayOfMonth
                                    } else {
                                        latestSchedule.dayOfMonth
                                    }
                                }일",
                                dayOfWeek = listOf(
                                    "월",
                                    "화",
                                    "수",
                                    "목",
                                    "금",
                                    "토",
                                    "일",
                                )[
                                    if (current == latestSchedule) {
                                        current.dayOfWeek.isoDayNumber - 1
                                    } else {
                                        latestSchedule.dayOfWeek.isoDayNumber - 1
                                    },
                                ] + "요일",
                                body = remember {
                                    schedules.filter {
                                        latestSchedule in it.date
                                    }.toImmutableList()
                                },
                            )

                            val nextSchedule = remember {
                                if (schedules.first().date.size > 1 &&
                                    schedules[0].date[0] != schedules[0].date[1]
                                ) {
                                    if (tomorrow in schedules.first().date) {
                                        tomorrow
                                    } else {
                                        null
                                    }
                                } else {
                                    if (schedules.size > 1) {
                                        schedules[1].date.first()
                                    } else {
                                        null
                                    }
                                }
                            }

                            nextSchedule?.let {
                                ScheduleComponent(
                                    day = "${it.dayOfMonth}일",
                                    dayOfWeek = listOf(
                                        "월",
                                        "화",
                                        "수",
                                        "목",
                                        "금",
                                        "토",
                                        "일",
                                    )[it.dayOfWeek.isoDayNumber - 1] + "요일",
                                    body = remember {
                                        schedules.filter { schedule ->
                                            it in schedule.date
                                        }.toImmutableList()
                                    },
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
                        DodamLoadingDots()
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ScheduleComponent(
    modifier: Modifier =
        Modifier,
    day: String,
    dayOfWeek: String,
    body: ImmutableList<Schedule>,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = day,
                style = DodamTheme.typography.heading2Bold(),
                color = DodamTheme.colors.labelNormal,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = dayOfWeek,
                style = DodamTheme.typography.labelMedium(),
                color = DodamTheme.colors.labelAlternative,
            )
        }
        Spacer(Modifier.height(8.dp))
        body.fastForEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
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

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    modifier = Modifier.basicMarquee(),
                    text = it.name,
                    style = DodamTheme.typography.body1Medium().copy(
                        lineHeight = 19.sp,
                    ),
                    color = DodamTheme.colors.labelNeutral,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
