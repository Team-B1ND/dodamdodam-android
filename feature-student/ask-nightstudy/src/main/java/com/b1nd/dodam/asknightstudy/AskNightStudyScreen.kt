package com.b1nd.dodam.asknightstudy

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.b1nd.dodam.data.core.model.Place
import com.b1nd.dodam.dds.component.DodamCheckbox
import com.b1nd.dodam.dds.component.DodamDialog
import com.b1nd.dodam.dds.component.DodamSmallTopAppBar
import com.b1nd.dodam.dds.component.DodamTextField
import com.b1nd.dodam.dds.component.button.DodamCTAButton
import com.b1nd.dodam.dds.component.button.DodamSegment
import com.b1nd.dodam.dds.component.button.DodamSegmentedButtonRow
import com.b1nd.dodam.dds.component.button.DodamTextButton
import com.b1nd.dodam.dds.foundation.DodamShape
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.CheckmarkIcon
import com.b1nd.dodam.dds.style.ChevronRightIcon
import com.b1nd.dodam.dds.style.TitleLarge
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
internal fun AskNightStudyScreen(
    viewModel: AskNightStudyViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    var nightStudyReason by remember { mutableStateOf("") }

    var nightStudyStartDate by remember { mutableStateOf(LocalDate.now()) }
    var nightStudyEndDate by remember { mutableStateOf(LocalDate.now().plusDays(14)) }

    var nightStudyPlace by remember { mutableStateOf(Place.PROGRAMMING_1) }

    var showDatePicker by remember { mutableStateOf(Pair(false, "시작")) }
    var showPlacePicker by remember { mutableStateOf(false) }

    var doNeedPhone by remember { mutableStateOf(false) }
    var reasonForPhone by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect {
            when (it) {
                is Event.ShowDialog -> {
                    showDialog = true
                }

                is Event.Success -> {
                    popBackStack()
                }
            }
        }
    }

    if (showDialog) {
        DodamDialog(
            onDismissRequest = { showDialog = false },
            confirmText = {
                DodamTextButton(onClick = { showDialog = false }) {
                    Text(text = "확인")
                }
            },
            title = { Text(text = "심야 자습을 신청할 수 없어요") },
            text = { Text(text = uiState.message) }
        )
    }

    if (showDatePicker.first) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            onDismissRequest = { showDatePicker = Pair(false, "") }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )  {
                TitleLarge(
                    modifier = Modifier.padding(8.dp),
                    text = if (showDatePicker.second == "시작") {
                        "시작일자"
                    } else {
                        "복귀일자"
                    },
                    fontSize = 20.sp
                )
                WheelDatePicker(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    startDate = if (showDatePicker.second == "시작") {
                        nightStudyStartDate
                    } else {
                        nightStudyEndDate
                    },
                    minDate = LocalDate.now(),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
                    textColor = MaterialTheme.colorScheme.onSurface,
                    onSnappedDate = {
                        if (showDatePicker.second == "시작") {
                            nightStudyStartDate = it
                        } else {
                            nightStudyEndDate = it
                        }
                    },
                )
            }
        }
    }

    if (showPlacePicker) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            onDismissRequest = { showPlacePicker = false }
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TitleLarge(
                    modifier = Modifier.padding(8.dp),
                    text = "자습 장소",
                    fontSize = 20.sp
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(
                        items = Place.entries,
                        key = { it.name },
                    ) {
                        InputField(
                            onClick = {
                                nightStudyPlace = it
                            },
                            text = {
                                BodyLarge(
                                    text = it.place,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.tertiary,
                                )
                            },
                            content = {
                                if (it == nightStudyPlace) {
                                    CheckmarkIcon(
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
                        )
                    }
                }
            }
        }
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            DodamSmallTopAppBar(
                title = { Text(text = "심야 자습 신청하기") },
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
                    .padding(horizontal = 16.dp)
                    .imePadding()
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    DodamTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = nightStudyReason,
                        onValueChange = { nightStudyReason = it },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        label = { Text(text = "심야 자습 사유") },
                        isError = nightStudyReason.length !in 10..250 && uiState.message.isNotBlank(),
                        supportingText = if (nightStudyReason.length !in 10..250 && uiState.message.isNotBlank()) {
                            { Text(text = "사유를 10자 이상 입력해주세요.") }
                        } else null
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                InputField(
                    onClick = {
                        showPlacePicker = true
                    },
                    text = {
                        BodyLarge(
                            text = "자습 장소",
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
                                text = nightStudyPlace.place,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            ChevronRightIcon(
                                modifier = Modifier
                                    .size(16.dp)
                                    .rotate(90f),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                InputField(
                    onClick = {
                        showDatePicker = Pair(true, "시작")
                    },
                    text = {
                        BodyLarge(
                            text = "시작일자",
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
                                text = nightStudyStartDate.format(DateTimeFormatter.ofPattern("MM월 dd일")),
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
                        showDatePicker = Pair(true, "복귀")
                    },
                    text = {
                        BodyLarge(
                            text = "복귀일자",
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
                                text = nightStudyEndDate.format(DateTimeFormatter.ofPattern("MM월 dd일")),
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
                        doNeedPhone = !doNeedPhone
                    },
                    text = {
                        BodyLarge(
                            text = "휴대폰 사용",
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                    },
                    content = {
                        DodamCheckbox(
                            checked = doNeedPhone,
                            onCheckedChange = { doNeedPhone = !doNeedPhone })
                    },
                )
                AnimatedVisibility(doNeedPhone) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        DodamTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = reasonForPhone,
                            onValueChange = { reasonForPhone = it },
                            textStyle = MaterialTheme.typography.bodyLarge,
                            label = { Text(text = "휴대폰 사용 사유") },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }

            DodamCTAButton(
                modifier = Modifier.imePadding(),
                onClick = {
                    viewModel.askNightStudy(
                        place = nightStudyPlace,
                        content = nightStudyReason,
                        doNeedPhone = doNeedPhone,
                        reasonForPhone = if (doNeedPhone) reasonForPhone else null,
                        startAt = nightStudyStartDate.toKotlinLocalDate(),
                        endAt = nightStudyEndDate.toKotlinLocalDate(),
                    )
                },
                enabled = nightStudyReason.isNotBlank(),
                isLoading = uiState.isLoading
            ) {
                Text(text = "확인")
            }
        }
    }
}
