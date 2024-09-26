package com.b1nd.dodam.askout

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.common.utiles.plusHour
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.CalendarDate
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamButtonDialog
import com.b1nd.dodam.designsystem.component.DodamDatePickerBottomSheet
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTimePickerBottomSheet
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.rememberDodamDatePickerState
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.util.addFocusCleaner
import java.time.ZoneId
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaLocalDate
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
internal fun AskOutScreen(viewModel: AskOutViewModel = koinViewModel(), popBackStack: () -> Unit, showToast: (String, String) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    var selectedItem by remember { mutableStateOf("외출") }

    var outingReason by remember { mutableStateOf("") }
    var sleepoverReason by remember { mutableStateOf("") }

    var outingDate by remember { mutableStateOf(DodamDate.localDateNow()) }
    var outingStartTime by remember { mutableStateOf(DodamDate.localTimeNow()) }
    var outingEndTime by remember { mutableStateOf(DodamDate.localTimeNow().plusHour(1)) }

    var sleepoverStartDate by remember { mutableStateOf(DodamDate.localDateNow()) }
    var sleepoverEndDate by remember { mutableStateOf(DodamDate.localDateNow().plus(DatePeriod(days = 2))) }

    // Pair<Boolean(show 여부), Boolean(true: 시작, false: 복귀)>
    var showTimePicker by remember { mutableStateOf(false to true) }
    // Triple<Boolean(show 여부), String, Boolean(true: 시작, false: 복귀)>
    var showDatePicker by remember { mutableStateOf(Triple(false, "외박", true)) }
    var showMealPicker by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDodamDatePickerState(initialSelectDateMillis = outingDate.toJavaLocalDate().getUtcTimeMill())

    var temporaryDate by remember { mutableStateOf(DodamDate.localDateNow()) }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect {
            when (it) {
                Event.Success -> {
                    showToast("SUCCESS", if (selectedItem.isOut()) "외출 신청을 성공했어요" else "외박 신청을 성공했어요")
                    popBackStack()
                }
                Event.ShowDialog -> {
                    showDialog = true
                }
            }
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
        ) {
            DodamDialog(
                confirmButton = { showDialog = false },
                title = "${if (selectedItem.isOut()) "외출" else "외박"}을 신청할 수 없어요",
                body = uiState.message,
            )
        }
    }

    if (showDatePicker.first) {
        DodamDatePickerBottomSheet(
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
            ),
            state = datePickerState,
            shape = DodamTheme.shapes.extraLarge.copy(
                bottomStart = CornerSize(0.dp),
                bottomEnd = CornerSize(0.dp),
            ),
            onDismissRequest = {
                showDatePicker = Triple(false, "외출", true)
            },
            onClickDate = { date, isValid ->
                if (!isValid) {
                    return@DodamDatePickerBottomSheet
                }
                temporaryDate = LocalDate(date.year, date.month, date.dayOfMonth)
                datePickerState.selectedDate = date
            },
            onClickSuccess = {
                when {
                    showDatePicker.second.isOut() -> {
                        outingDate = temporaryDate
                    }
                    showDatePicker.third -> {
                        sleepoverStartDate = temporaryDate
                    }
                    showDatePicker.third.not() -> {
                        sleepoverEndDate = temporaryDate
                    }
                }
                showDatePicker = Triple(false, "외출", true)
            },
        )
    }

    val startTime = if (showTimePicker.second) outingStartTime.hour else outingEndTime.hour
    if (showTimePicker.first) {
        DodamTimePickerBottomSheet(
            onDismissRequest = {
                showTimePicker = false to false
            },
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
            ),
            startTime = if (startTime == 0) startTime + 1 else startTime,
            startMinute = if (showTimePicker.second) outingStartTime.minute else outingEndTime.minute,
            onSelectTime = { hour, minute ->
                if (showTimePicker.second) {
                    outingStartTime = LocalTime(hour, minute)
                } else {
                    outingEndTime = LocalTime(hour, minute)
                }
                showTimePicker = false to false
            },
            shape = DodamTheme.shapes.extraLarge.copy(
                bottomStart = CornerSize(0.dp),
                bottomEnd = CornerSize(0.dp),
            ),
        )
    }

    if (showMealPicker) {
        Dialog(
            onDismissRequest = { showMealPicker = false },
        ) {
            DodamButtonDialog(
                confirmButton = {
                    viewModel.askOuting(
                        reason = outingReason,
                        startAt = LocalDateTime(outingDate, outingStartTime),
                        endAt = LocalDateTime(outingDate, outingEndTime),
                        isDinner = true,
                    )
                },
                confirmButtonText = "네, 먹습니다",
                confirmButtonRole = ButtonRole.Primary,
                dismissButton = {
                    viewModel.askOuting(
                        reason = outingReason,
                        startAt = LocalDateTime(outingDate, outingStartTime),
                        endAt = LocalDateTime(outingDate, outingEndTime),
                        isDinner = false,
                    )
                },
                dismissButtonText = "아니요",
                dismissButtonRole = ButtonRole.Assistive,
                title = "오늘 저녁 급식을 드시나요? \uD83E\uDD7A",
                body = "급식 수요조사를 위해 알려주시면 감사드리겠습니다",
            )
        }
    }

    Scaffold(
        modifier = Modifier
            .addFocusCleaner(focusManager),
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "외출/외박 신청하기",
                onBackClick = popBackStack,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            DodamSegmentedButton(
                segments = persistentListOf(
                    DodamSegment(
                        selected = selectedItem.isOut(),
                        onClick = { selectedItem = "외출" },
                        text = "외출",
                    ),
                    DodamSegment(
                        selected = !selectedItem.isOut(),
                        onClick = { selectedItem = "외박" },
                        text = "외박",
                    ),
                ),
            )
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                DodamTextField(
                    value = if (selectedItem.isOut()) outingReason else sleepoverReason,
                    onValueChange = {
                        if (selectedItem.isOut()) {
                            outingReason = it
                        } else {
                            sleepoverReason = it
                        }
                    },
                    label = "$selectedItem 사유",
                    onClickRemoveRequest = {
                        if (selectedItem.isOut()) {
                            outingReason = ""
                        } else {
                            sleepoverReason = ""
                        }
                    },
                )

                if (selectedItem.isOut()) {
                    AskOutButton(
                        title = "외출 날짜",
                        description = outingDate.toDateString(),
                        onClick = {
                            temporaryDate = outingDate
                            datePickerState.selectedDate = outingDate.toCalendarDate()
                            showDatePicker = Triple(true, "외출", true)
                        },
                    )
                }

                AskOutButton(
                    title = "$selectedItem ${if (selectedItem.isOut()) "시간" else "날짜"}",
                    description = if (selectedItem.isOut()) outingStartTime.toHourMinString() else sleepoverStartDate.toDateString(),
                    onClick = {
                        if (selectedItem.isOut()) {
                            showTimePicker = true to true
                        } else {
                            temporaryDate = sleepoverStartDate
                            datePickerState.selectedDate = sleepoverStartDate.toCalendarDate()
                            showDatePicker = Triple(true, "외박", true)
                        }
                    },
                )

                AskOutButton(
                    title = "복귀 ${if (selectedItem.isOut()) "시간" else "날짜"}",
                    description = if (selectedItem.isOut()) outingEndTime.toHourMinString() else sleepoverEndDate.toDateString(),
                    onClick = {
                        if (selectedItem.isOut()) {
                            showTimePicker = true to false
                        } else {
                            temporaryDate = sleepoverEndDate
                            datePickerState.selectedDate = sleepoverEndDate.toCalendarDate()
                            showDatePicker = Triple(true, "외박", false)
                        }
                    },
                )

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(20.dp))
                DodamButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        when {
                            selectedItem.isOut() && outingDate.dayOfWeek == java.time.DayOfWeek.WEDNESDAY -> {
                                showMealPicker = true
                            }
                            selectedItem.isOut() -> {
                                viewModel.askOuting(
                                    reason = outingReason,
                                    startAt = LocalDateTime(outingDate, outingStartTime),
                                    endAt = LocalDateTime(outingDate, outingEndTime),
                                )
                            }
                            else -> {
                                viewModel.askSleepover(
                                    reason = sleepoverReason,
                                    startAt = sleepoverStartDate,
                                    endAt = sleepoverEndDate,
                                )
                            }
                        }
                    },
                    text = "신청",
                    buttonRole = ButtonRole.Primary,
                    buttonSize = ButtonSize.Large,
                )
            }
        }
    }
}

@Composable
private fun AskOutButton(modifier: Modifier = Modifier, title: String, description: String, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(),
                onClick = onClick,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = title,
            color = DodamTheme.colors.labelAlternative,
            style = DodamTheme.typography.headlineMedium(),
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = description,
            color = DodamTheme.colors.primaryNormal,
            style = DodamTheme.typography.headlineRegular(),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Image(
            imageVector = DodamIcons.Calendar.value,
            contentDescription = "calendar",
            colorFilter = ColorFilter.tint(DodamTheme.colors.primaryNormal),
        )
    }
}

private fun String.isOut() = this == "외출"

private fun LocalTime.toHourMinString(): String = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"

private fun LocalDate.toDateString(): String = "${monthNumber}월 ${dayOfMonth}일 (${dayOfWeek.toShortKoreanWeek()})"

private fun DayOfWeek.toShortKoreanWeek() = when (this) {
    DayOfWeek.MONDAY -> "월"
    DayOfWeek.TUESDAY -> "화"
    DayOfWeek.WEDNESDAY -> "수"
    DayOfWeek.THURSDAY -> "목"
    DayOfWeek.FRIDAY -> "금"
    DayOfWeek.SATURDAY -> "토"
    DayOfWeek.SUNDAY -> "일"
}

private val utcTimeZoneId: ZoneId = ZoneId.of("UTC")

private fun java.time.LocalDate.getUtcTimeMill(): Long = this
    .atTime(java.time.LocalTime.MIDNIGHT)
    .atZone(utcTimeZoneId)
    .toInstant()
    .toEpochMilli()

private fun LocalDate.toCalendarDate(): CalendarDate = CalendarDate(year, monthNumber, dayOfMonth, this.toJavaLocalDate().getUtcTimeMill())
