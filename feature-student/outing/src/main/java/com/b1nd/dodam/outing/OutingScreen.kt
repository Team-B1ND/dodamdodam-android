package com.b1nd.dodam.outing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.data.outing.model.OutType
import com.b1nd.dodam.data.outing.model.Outing
import com.b1nd.dodam.designsystem.component.DodamAskCard
import com.b1nd.dodam.designsystem.component.DodamFullWidthButton
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.icons.ConvenienceStore
import com.b1nd.dodam.designsystem.icons.Plus
import com.b1nd.dodam.designsystem.icons.Tent
import com.b1nd.dodam.outing.viewmodel.OutingViewModel
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlinx.datetime.toJavaLocalDateTime

@Composable
fun OutingScreen(onAddOutingClick: () -> Unit, onAddSleepOverClick: () -> Unit, viewModel: OutingViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var isOutSleeping by remember {
        mutableStateOf(false)
    }
    val current = LocalDateTime.now()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DodamTopAppBar(
            title = "외출/외박",
            containerColor = MaterialTheme.colorScheme.surface,
            icon = Plus,
            onIconClick = onAddOutingClick,
            contentColor = MaterialTheme.colorScheme.onBackground,
        )
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            DodamSegmentedButton(
                titles = listOf("외출", "외박"),
                onClick = { index ->
                    isOutSleeping = when (index) {
                        1 -> true
                        else -> false
                    }
                },
            )

            Spacer(modifier = Modifier.height(20.dp))

            val outingsList: List<Outing> = (uiState.outings ?: emptyList())

            if (outingsList.isNotEmpty()) {
                LazyColumn {
                    items(
                        (uiState.outings ?: emptyList())
                            .filter { outing ->
                                outing.outType == if (isOutSleeping) OutType.SLEEPOVER else OutType.OUTING
                            },
                        key = { outing ->
                            outing.id
                        },
                    ) { outing ->
                        val outingProgress by remember {
                            mutableFloatStateOf(
                                1 - ChronoUnit.SECONDS.between(
                                    outing.startAt.toJavaLocalDateTime(),
                                    current,
                                ).toFloat() / ChronoUnit.SECONDS.between(
                                    outing.startAt.toJavaLocalDateTime(),
                                    outing.endAt.toJavaLocalDateTime(),
                                ),
                            )
                        }

                        val day = ChronoUnit.DAYS.between(
                            current,
                            outing.endAt.toJavaLocalDateTime(),
                        )
                        val hour = ChronoUnit.HOURS.between(
                            current,
                            outing.endAt.toJavaLocalDateTime(),
                        )
                        val minute =
                            ChronoUnit.MINUTES.between(
                                current,
                                outing.endAt.toJavaLocalDateTime(),
                            )

                        val currentLeftTime =
                            if (day > 0) {
                                "${day}일 "
                            } else if (hour > 0) {
                                "${hour}시간 "
                            } else {
                                "${minute}분 "
                            }

                        val endTimeMessage = "복귀"
                        val startTimeMessage: String
                        val startTimeText: String
                        val endTimeText: String

                        val dayOfWeek = when (current.dayOfWeek.value) {
                            1 -> "월"
                            2 -> "화"
                            3 -> "수"
                            4 -> "목"
                            5 -> "금"
                            6 -> "토"
                            else -> "일"
                        }
                        val labelText =
                            "${current.monthValue}월 ${current.dayOfMonth}일 ($dayOfWeek)"
                        when (isOutSleeping) {
                            true -> {
                                startTimeMessage = "외박"
                                startTimeText =
                                    "${outing.startAt.monthNumber}월 ${outing.startAt.dayOfMonth}일"
                                endTimeText =
                                    "${outing.endAt.monthNumber}월 ${outing.endAt.dayOfMonth}일"
                            }

                            false -> {
                                startTimeMessage = "외출"
                                startTimeText =
                                    "${outing.startAt.hour}시 ${outing.startAt.minute}분"
                                endTimeText = "${outing.endAt.hour}시 ${outing.endAt.minute}분"
                            }
                        }

                        DodamAskCard(
                            askStatus = uiState.toString(),
                            labelText = labelText,
                            startTime = startTimeText,
                            startTimeText = startTimeMessage,
                            endTime = endTimeText,
                            endTimeText = endTimeMessage,
                            currentLeftTime = currentLeftTime,
                            reason = outing.reason,
                            rejectedReason = null,
                            phoneReason = null,
                            progress = outingProgress,
                        )
                    }
                }
            }

            val isOutingsEmpty = outingsList.filter { outing ->
                outing.outType == OutType.OUTING
            }.isEmpty()

            val isSleepOversEmpty = outingsList.filter { outing ->
                outing.outType == OutType.SLEEPOVER
            }.isEmpty()

            if ((isOutingsEmpty && !isOutSleeping) || (isSleepOversEmpty && isOutSleeping)) {
                val icon: ImageVector
                val type: String
                if (isOutSleeping) {
                    icon = Tent
                    type = "외박"
                } else {
                    icon = ConvenienceStore
                    type = "외출"
                }
                Column(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.background,
                            RoundedCornerShape(18.dp),
                        )
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(imageVector = icon, contentDescription = null)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "아직 신청한 ${type}이 없어요",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    DodamFullWidthButton(
                        onClick = { if (isOutSleeping) onAddSleepOverClick() else onAddOutingClick() },
                        text = "$type 신청하기",
                        colors = ButtonColors(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.onSurfaceVariant,
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewOutingScreen() {
    OutingScreen(
        onAddOutingClick = { /*TODO*/ },
        onAddSleepOverClick = { /*TODO*/ },
    )
}
