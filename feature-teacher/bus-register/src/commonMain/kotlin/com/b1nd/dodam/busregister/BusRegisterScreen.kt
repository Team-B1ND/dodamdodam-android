package com.b1nd.dodam.busregister

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDatePickerBottomSheet
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTimePickerBottomSheet
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.designsystem.component.rememberDodamDatePickerState
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BusRegisterScreen(
    popBackStack: () -> Unit,
    navigateToBusPreset: () -> Unit,
) {

    var busName by remember { mutableStateOf("") }
    var busDescription by remember { mutableStateOf("") }
    var busStartDate by remember { mutableStateOf(DodamDate.now()) }
    var busRequiredTime by remember { mutableStateOf(LocalTime(hour = 1, minute = 0)) }
    var peopleLimit by remember { mutableStateOf(0) }

    var isDatePicking by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDodamDatePickerState(initialSelectDateMillis = busStartDate.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds())

    if (showDatePicker) {
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
                isDatePicking = false
                showDatePicker = false
            },
            onClickDate = { date, isValid ->
                if (!isValid) {
                    return@DodamDatePickerBottomSheet
                }
                val temporaryDate = LocalDate(date.year, date.month, date.dayOfMonth)
                busStartDate = LocalDateTime(temporaryDate, busStartDate.time)
                datePickerState.selectedDate = date
            },
            onClickSuccess = {
                showDatePicker = false
                if (isDatePicking) {
                    showTimePicker = true
                }
            },
        )
    }

    if (showTimePicker) {
        DodamTimePickerBottomSheet(
            onDismissRequest = {
                showTimePicker = false
                isDatePicking = false
            },
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
            ),
            startTime = if (isDatePicking) busStartDate.hour else busRequiredTime.hour,
            startMinute = if (isDatePicking) busStartDate.minute else busRequiredTime.minute,
            onSelectTime = { hour, minute ->
                val localTime = LocalTime(hour = hour, minute = minute)
                if (isDatePicking) {
                    busStartDate = LocalDateTime(busStartDate.date, localTime)
                } else {
                    busRequiredTime = localTime
                }
                showTimePicker = false
                isDatePicking = false
            },
            shape = DodamTheme.shapes.extraLarge.copy(
                bottomStart = CornerSize(0.dp),
                bottomEnd = CornerSize(0.dp),
            ),
        )
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "버스 등록",
                type = TopAppBarType.Small,
                onBackClick = popBackStack,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp,
                ),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DodamTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = busName,
                    onValueChange = {
                        busName = it
                    },
                    label = "버스 이름",
                )

                DodamTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = busDescription,
                    onValueChange = {
                        busDescription = it
                    },
                    label = "버스 설명",
                )

                BusRegisterButton(
                    title = "출발 시간",
                    description = "${busStartDate.monthNumber}월 ${busStartDate.dayOfMonth}일 ${busStartDate.hour.timeFormat()}:${busStartDate.minute.timeFormat()}",
                    onClick = {
                        isDatePicking = true
                        showDatePicker = true
                    }
                )
                BusRegisterButton(
                    title = "소요 시간",
                    description = "${busRequiredTime.hour.timeFormat()}:${busRequiredTime.minute.timeFormat()}",
                    onClick = {
                        showTimePicker = true
                    },
                )

                DodamTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = peopleLimit.toString(),
                    onValueChange = {
                        if (it.toIntOrNull() != null) {
                            peopleLimit = it.toInt()
                        }
                        if (it == "") {
                            peopleLimit = 0
                        }
                    },
                    onClickRemoveRequest = {
                        peopleLimit = 0
                    },
                    label = "인원 제한",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 12.dp)
            ) {
                DodamButton(
                    onClick = navigateToBusPreset,
                    text = "프리셋",
                    buttonSize = ButtonSize.Large,
                    buttonRole = ButtonRole.Assistive,
                    modifier = Modifier.weight(2f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                DodamButton(
                    onClick = {

                    },
                    text = "등록",
                    buttonSize = ButtonSize.Large,
                    buttonRole = ButtonRole.Primary,
                    modifier = Modifier.weight(3f),
                )
            }
        }
    }
}

@Composable
private fun BusRegisterButton(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: DodamIcons = DodamIcons.Calendar,
    onClick: () -> Unit
) {
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
            imageVector = icon.value,
            contentDescription = null,
            colorFilter = ColorFilter.tint(DodamTheme.colors.primaryNormal),
        )
    }
}

/**
 * @return String, example input 5 -> `05`
 */
private fun Int.timeFormat() =
    this.toString().padStart(2, padChar = '0')