package com.b1nd.dodam.bus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.b1nd.dodam.bus.model.Bus
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.common.utiles.minus
import com.b1nd.dodam.common.utiles.timeFormat
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DividerType
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.designsystem.component.DodamDivider
import com.b1nd.dodam.designsystem.component.DodamModalBottomSheet
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.ui.component.InputField
import com.b1nd.dodam.ui.component.modifier.`if`
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.koin.androidx.compose.koinViewModel
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusScreen(viewModel: BusViewModel = koinViewModel(), popBackStack: () -> Unit, showToast: (String, String) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    var currentTime by remember { mutableStateOf(DodamDate.now()) }
    var toastMessage by remember {
        mutableStateOf("")
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    var bottomSheetBus: Bus? by remember { mutableStateOf(null) }

    LaunchedEffect(key1 = toastMessage) {
        if (toastMessage.isNotEmpty()) {
            if (uiState.isError) {
                showToast("ERROR", toastMessage)
            } else {
                showToast("SUCCESS", toastMessage)
                popBackStack()
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when (event) {
                is Event.ShowToast -> {
                    toastMessage = event.message
                }

                is Event.ShowDialog -> {
                    showDialog = true
                }
            }
        }
    }

    LaunchedEffect(true) {
        while (true) {
            delay(5000)
            currentTime = DodamDate.now()
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
                popBackStack()
            },
        ) {
            DodamDialog(
                confirmButton = popBackStack,
                title = "버스 신청자가 아닙니다",
            )
        }
    }

    if (bottomSheetBus != null) {
        DodamModalBottomSheet(
            shape = DodamTheme.shapes.extraLarge.copy(
                bottomStart = ZeroCornerSize,
                bottomEnd = ZeroCornerSize,
            ),
            onDismissRequest = {
                bottomSheetBus = null
            },
            title = {
                Text(
                    text = "동대구역 1호",
                    style = DodamTheme.typography.heading1Bold(),
                    color = DodamTheme.colors.labelNormal,
                )
            },
            content = {
                Text(
                    text = "동대구역에 하차합니다.",
                    style = DodamTheme.typography.headlineMedium(),
                    color = DodamTheme.colors.labelAssistive,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "출발 시간: ${bottomSheetBus!!.leaveTime.hour.timeFormat()}:${bottomSheetBus!!.leaveTime.minute.timeFormat()}",
                    style = DodamTheme.typography.headlineMedium(),
                    color = DodamTheme.colors.labelAssistive,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "소요 시간: ${bottomSheetBus!!.timeRequired.hour.timeFormat()}:${bottomSheetBus!!.timeRequired.minute.timeFormat()}",
                    style = DodamTheme.typography.headlineMedium(),
                    color = DodamTheme.colors.labelAssistive,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "탑승자 수: ${bottomSheetBus!!.applyCount}/${bottomSheetBus!!.peopleLimit}",
                    style = DodamTheme.typography.headlineMedium(),
                    color = DodamTheme.colors.labelAssistive,
                )
                Spacer(modifier = Modifier.height(24.dp))
                DodamButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {},
                    text = "버스 신청",
                    buttonRole = ButtonRole.Primary,
                    buttonSize = ButtonSize.Large,
                )
            }
        )
    }

    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "귀가 버스 신청",
                onBackClick = popBackStack,
                type = TopAppBarType.Medium,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 12.dp),
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "신청된 버스",
                        style = DodamTheme.typography.headlineBold(),
                        color = DodamTheme.colors.labelNormal,
                    )
                    if (uiState.selectedBus == null) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            text = "신청된 버스가 아직 없어요.",
                            style = DodamTheme.typography.body1Medium(),
                            color = DodamTheme.colors.labelAssistive,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = DodamTheme.colors.backgroundNormal,
                                    shape = DodamTheme.shapes.large
                                )
                                .padding(16.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 2.dp),
                                text = uiState.selectedBus!!.busName,
                                style = DodamTheme.typography.body2Medium(),
                                color = DodamTheme.colors.labelAssistive,
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(
                                modifier = Modifier.padding(2.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.Bottom,
                            ) {
                                val duration = DodamDate.now() - currentTime
                                val hours = duration.toInt(DurationUnit.HOURS)
                                val minutes = (duration - hours.toDuration(DurationUnit.HOURS)).toInt(DurationUnit.MINUTES)
                                Text(
                                    text = "${hours}시간 ${minutes}분",
                                    style = DodamTheme.typography.title3Medium(),
                                    color = DodamTheme.colors.labelNormal,
                                )
                                Text(
                                    text = "남음",
                                    style = DodamTheme.typography.labelMedium(),
                                    color = DodamTheme.colors.labelAssistive,
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            DodamButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "QR 태깅",
                                buttonSize = ButtonSize.Large,
                                buttonRole = ButtonRole.Assistive,
                                onClick = {

                                }
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                DodamDivider(
                    type = DividerType.Normal
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.padding(
                        start = 24.dp,
                        top = 8.dp
                    ),
                    text = "버스 신청",
                    style = DodamTheme.typography.body2Medium(),
                    color = DodamTheme.colors.labelAlternative,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(uiState.buses.size) { index ->
                val bus = uiState.buses[index]
                BusCard(
                    bus = bus,
                    isDisabled = bus.applyCount == bus.peopleLimit,
                    onClick = {
                        bottomSheetBus = bus
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun BusCard(bus: Bus, isDisabled: Boolean, onClick: () -> Unit) {
    InputField(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .`if`(isDisabled) {
                alpha(0.4f)
            },
        onClick = onClick,
        enabled = !isDisabled,
        text = {
            Text(
                text = bus.busName,
                style = DodamTheme.typography.headlineMedium(),
                color = DodamTheme.colors.labelNormal,
            )
        },
        content = {
            Text(
                text = "${bus.applyCount}/${bus.peopleLimit}",
                style = DodamTheme.typography.headlineRegular(),
                color = DodamTheme.colors.labelNormal,
            )
        },
    )
}