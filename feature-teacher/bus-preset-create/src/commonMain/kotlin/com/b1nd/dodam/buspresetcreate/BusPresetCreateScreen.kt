package com.b1nd.dodam.buspresetcreate

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTimePickerBottomSheet
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BusPresetCreateScreen(
    popBackStack: () -> Unit
) {
    var busName by remember { mutableStateOf("") }
    var busDescription by remember { mutableStateOf("") }
    var busStartTime by remember { mutableStateOf(LocalTime(hour = 13, minute = 0)) }
    var busRequiredTime by remember { mutableStateOf(LocalTime(hour = 1, minute = 0)) }
    var peopleLimit by remember { mutableStateOf(0) }

    var isStartTimePicking by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    if (showTimePicker) {
        DodamTimePickerBottomSheet(
            onDismissRequest = {
                showTimePicker = false
                isStartTimePicking = false
            },
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
            ),
            startTime = if (isStartTimePicking) busStartTime.hour else busRequiredTime.hour,
            startMinute = if (isStartTimePicking) busStartTime.minute else busRequiredTime.minute,
            onSelectTime = { hour, minute ->
                val localTime = LocalTime(hour = hour, minute = minute)
                if (isStartTimePicking) {
                    busStartTime = localTime
                } else {
                    busRequiredTime = localTime
                }
                showTimePicker = false
                isStartTimePicking = false
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
                title = "버스 등록 프리셋 생성",
                type = TopAppBarType.Small,
                onBackClick = popBackStack,
            )
        },
        containerColor = DodamTheme.colors.backgroundNormal,
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

                BusPresetButton(
                    title = "출발 시간",
                    description = "${busStartTime.hour.timeFormat()}:${busStartTime.minute.timeFormat()}",
                    onClick = {
                        isStartTimePicking = true
                        showTimePicker = true
                    },
                )
                BusPresetButton(
                    title = "소요 시간",
                    description = "${busRequiredTime.hour.timeFormat()}:${busRequiredTime.minute.timeFormat()}",
                    onClick = {
                        showTimePicker = true
                    },
                    icon = DodamIcons.Clock
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
            DodamButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                onClick = {

                },
                text = "생성",
                buttonSize = ButtonSize.Large,
                buttonRole = ButtonRole.Primary,
            )
        }
    }
}

@Composable
private fun BusPresetButton(
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