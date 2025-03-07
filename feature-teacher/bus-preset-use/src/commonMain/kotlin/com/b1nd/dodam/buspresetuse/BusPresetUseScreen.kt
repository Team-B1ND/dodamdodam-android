package com.b1nd.dodam.buspresetuse

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.ZeroCornerSize
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
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.common.utiles.timeFormat
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDatePickerBottomSheet
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
internal fun BusPresetUseScreen(
    popBackStack: () -> Unit,
) {

    var busStartDate by remember { mutableStateOf(DodamDate.now()) }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDodamDatePickerState(initialSelectDateMillis = busStartDate.toInstant(
        TimeZone.currentSystemDefault()).toEpochMilliseconds())

    if (showDatePicker) {
        DodamDatePickerBottomSheet(
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
            ),
            state = datePickerState,
            shape = DodamTheme.shapes.extraLarge.copy(
                bottomStart = ZeroCornerSize,
                bottomEnd = ZeroCornerSize,
            ),
            onDismissRequest = {
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
            },
        )
    }

    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "버스 등록 프리셋 사용",
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
                    vertical = 8.dp,
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberBounceIndication(),
                        onClick = {
                            showDatePicker = true
                        },
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "출발 시간",
                    color = DodamTheme.colors.labelAlternative,
                    style = DodamTheme.typography.headlineMedium(),
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${busStartDate.monthNumber}월 ${busStartDate.dayOfMonth}일",
                    color = DodamTheme.colors.primaryNormal,
                    style = DodamTheme.typography.headlineRegular(),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    modifier = Modifier.size(24.dp),
                    imageVector = DodamIcons.Calendar.value,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(DodamTheme.colors.primaryNormal),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            DodamButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                onClick = {},
                text = "사용",
                buttonRole = ButtonRole.Primary,
                buttonSize = ButtonSize.Large,
            )
        }
    }
}