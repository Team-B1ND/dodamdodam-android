package com.b1nd.dodam.askout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.dds.component.DodamSmallTopAppBar
import com.b1nd.dodam.dds.component.DodamTextField
import com.b1nd.dodam.dds.component.button.DodamCTAButton
import com.b1nd.dodam.dds.component.button.DodamSegment
import com.b1nd.dodam.dds.component.button.DodamSegmentedButtonRow
import com.b1nd.dodam.dds.foundation.DodamShape
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.ChevronRightIcon
import com.b1nd.dodam.ui.component.InputField
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.commandiron.wheel_picker_compose.WheelDateTimePicker
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime

@ExperimentalMaterial3Api
@Composable
internal fun AskOutScreen(viewModel: AskOutViewModel = hiltViewModel(), popBackStack: () -> Unit) {
    val scrollState = rememberScrollState()
    var selectedIndex by remember { mutableIntStateOf(0) }

    var outingReason by remember { mutableStateOf("") }
    var sleepoverReason by remember { mutableStateOf("") }

    var outingStartDateTime by remember { mutableStateOf(LocalDateTime.now()) }
    var outingEndDateTime by remember { mutableStateOf(LocalDateTime.now().plusHours(1)) }

    var sleepoverStartDate by remember { mutableStateOf(LocalDate.now()) }
    var sleepoverEndDate by remember { mutableStateOf(LocalDate.now().plusDays(2)) }

    var showDateTimePicker by remember { mutableStateOf(false to "외출") }
    var showDatePicker by remember { mutableStateOf(false to "외박") }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect {
            when (it) {
                Event.Success -> {
                    popBackStack()
                }
            }
        }
    }

    if (showDateTimePicker.first) {
        BottomSheetDialog(
            onDismissRequest = { showDateTimePicker = Pair(false, "") },
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceContainerHigh,
                        DodamShape.ExtraLarge,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurfaceVariant,
                            CircleShape,
                        ),
                )

                Spacer(modifier = Modifier.height(12.dp))

                WheelDateTimePicker(
                    startDateTime = if (showDateTimePicker.second == "외출") {
                        outingStartDateTime
                    } else {
                        outingEndDateTime
                    },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
                    textColor = MaterialTheme.colorScheme.onSurface,
                    minDateTime = LocalDateTime.now(),
                    onSnappedDateTime = {
                        if (showDateTimePicker.second == "외출") {
                            outingStartDateTime = it
                        } else {
                            outingEndDateTime = it
                        }
                    },
                )
            }
        }
    }

    if (showDatePicker.first) {
        BottomSheetDialog(onDismissRequest = { showDatePicker = Pair(false, "") }) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceContainerHigh,
                        DodamShape.ExtraLarge,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurfaceVariant,
                            CircleShape,
                        ),
                )

                Spacer(modifier = Modifier.height(12.dp))

                WheelDatePicker(
                    startDate = if (showDatePicker.second == "외박") {
                        sleepoverStartDate
                    } else {
                        sleepoverEndDate
                    },
                    minDate = LocalDate.now(),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
                    textColor = MaterialTheme.colorScheme.onSurface,
                    onSnappedDate = {
                        if (showDatePicker.second == "외박") {
                            sleepoverStartDate = it
                        } else {
                            sleepoverEndDate = it
                        }
                    },
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            DodamSmallTopAppBar(
                title = { Text(text = "외출/외박 신청하기") },
                onNavigationIconClick = popBackStack,
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    DodamSegmentedButtonRow(selectedIndex = selectedIndex) {
                        DodamSegment(
                            selected = selectedIndex == 0,
                            onClick = { selectedIndex = 0 },
                        ) {
                            Text(text = "외출")
                        }
                        DodamSegment(
                            selected = selectedIndex == 1,
                            onClick = { selectedIndex = 1 },
                        ) {
                            Text(text = "외박")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    DodamTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = if (selectedIndex == 0) outingReason else sleepoverReason,
                        onValueChange = {
                            if (selectedIndex == 0) {
                                outingReason = it
                            } else {
                                sleepoverReason = it
                            }
                        },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        label = { Text(text = if (selectedIndex == 0) "외출 사유" else "외박 사유") },
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                InputField(
                    onClick = {
                        if (selectedIndex == 0) {
                            showDateTimePicker = Pair(true, "외출")
                        } else {
                            showDatePicker = Pair(true, "외박")
                        }
                    },
                    text = {
                        BodyLarge(
                            text = if (selectedIndex == 0) "외출일시" else "외박일자",
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                    },
                    content = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            BodyLarge(
                                text = if (selectedIndex == 0) {
                                    outingStartDateTime.format(DateTimeFormatter.ofPattern("MM월 dd일 HH:mm"))
                                } else {
                                    sleepoverStartDate.format(DateTimeFormatter.ofPattern("MM월 dd일"))
                                },
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            ChevronRightIcon(
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                InputField(
                    onClick = {
                        if (selectedIndex == 0) {
                            showDateTimePicker = Pair(true, "복귀")
                        } else {
                            showDatePicker = Pair(true, "복귀")
                        }
                    },
                    text = {
                        BodyLarge(
                            text = if (selectedIndex == 0) "복귀일시" else "복귀일자",
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                    },
                    content = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            BodyLarge(
                                text = if (selectedIndex == 0) {
                                    outingEndDateTime.format(DateTimeFormatter.ofPattern("MM월 dd일 HH:mm"))
                                } else {
                                    sleepoverEndDate.format(DateTimeFormatter.ofPattern("MM월 dd일"))
                                },
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            ChevronRightIcon(
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    },
                )
            }

            DodamCTAButton(
                modifier = Modifier.imePadding(),
                onClick = {
                    if (selectedIndex == 0) {
                        viewModel.askOuting(
                            outingReason,
                            outingStartDateTime.toKotlinLocalDateTime(),
                            outingEndDateTime.toKotlinLocalDateTime(),
                        )
                    } else {
                        viewModel.askSleepover(
                            sleepoverReason,
                            sleepoverStartDate.toKotlinLocalDate(),
                            sleepoverEndDate.toKotlinLocalDate(),
                        )
                    }
                },
                enabled = if (selectedIndex == 0) {
                    outingReason.isNotBlank()
                } else {
                    sleepoverReason.isNotBlank()
                },
            ) {
                Text(text = "확인")
            }
        }
    }
}
