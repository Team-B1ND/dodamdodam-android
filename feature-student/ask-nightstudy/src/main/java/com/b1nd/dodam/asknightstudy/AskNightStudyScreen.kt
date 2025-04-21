package com.b1nd.dodam.asknightstudy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.common.utiles.utcTimeMill
import com.b1nd.dodam.data.core.model.Place
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.CalendarDate
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamCheckBox
import com.b1nd.dodam.designsystem.component.DodamDatePickerBottomSheet
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.rememberDodamDatePickerState
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.component.InputField
import com.b1nd.dodam.ui.icons.UpDownArrow
import com.b1nd.dodam.ui.util.addFocusCleaner
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toKotlinLocalDate
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
internal fun AskNightStudyScreen(viewModel: AskNightStudyViewModel = koinViewModel(), popBackStack: () -> Unit, showToast: (String, String) -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    var nightStudyReason by remember { mutableStateOf("") }

    var nightStudyStartDate by remember { mutableStateOf(LocalDate.now()) }
    var nightStudyEndDate by remember { mutableStateOf(LocalDate.now().plusDays(13)) }

    var nightStudyPlace by remember { mutableStateOf(Place.PROJECT5) }

    var showDatePicker by remember { mutableStateOf(Pair(false, "시작")) }
    var showPlacePicker by remember { mutableStateOf(false) }

    var doNeedPhone by remember { mutableStateOf(false) }
    var reasonForPhone by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDodamDatePickerState()
    val nowDate = DodamDate.localDateNow()

    val focusManager = LocalFocusManager.current

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect {
            when (it) {
                is Event.ShowDialog -> {
                    showDialog = true
                }

                is Event.Success -> {
                    showToast("SUCCESS", "심야 자습 신청을 성공했어요")
                    popBackStack()
                }
            }
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
            },
        ) {
            DodamDialog(
                title = "심야 자습을 신청할 수 없어요",
                body = uiState.message,
                confirmButton = {
                    showDialog = false
                },
            )
        }
    }

    if (showDatePicker.first) {
        DodamDatePickerBottomSheet(
            onDismissRequest = { showDatePicker = Pair(false, "") },
            sheetState = rememberModalBottomSheetState(true),
            state = datePickerState,
            title = if (showDatePicker.second == "시작") "시작일자" else "종료일자",
            shape = RoundedCornerShape(
                topStart = DodamTheme.shapes.extraLarge.topStart,
                topEnd = DodamTheme.shapes.extraLarge.topEnd,
                bottomStart = CornerSize(0.dp),
                bottomEnd = CornerSize(0.dp),
            ),
            isValidDate = { date ->
                val kotlinDate = kotlinx.datetime.LocalDate(date.year, date.month, date.dayOfMonth)
                if (showDatePicker.second == "시작") {
                    return@DodamDatePickerBottomSheet 0 <= nowDate.daysUntil(kotlinDate)
                }

                val days = nightStudyStartDate
                    .toKotlinLocalDate()
                    .daysUntil(kotlinDate)
                days in 0..13
            },
            onClickDate = { date, isValid ->
                if (!isValid) return@DodamDatePickerBottomSheet

                val localDate = LocalDate.of(date.year, date.month, date.dayOfMonth)
                if (showDatePicker.second == "시작") {
                    nightStudyStartDate = localDate

                    val days = nightStudyStartDate
                        .toKotlinLocalDate()
                        .daysUntil(nightStudyEndDate.toKotlinLocalDate())
                    if (nightStudyStartDate > nightStudyEndDate) {
                        nightStudyEndDate = localDate
                    }

                    if (days > 13) {
                        nightStudyEndDate = nightStudyEndDate.minusDays((days - 13).toLong())
                    }
                } else {
                    nightStudyEndDate = localDate
                }
                datePickerState.selectedDate = date
            },
            onClickSuccess = {
                showDatePicker = Pair(false, "")
            },
        )
    }

    if (showPlacePicker) {
        ModalBottomSheet(
            modifier = Modifier.navigationBarsPadding(),
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            onDismissRequest = { showPlacePicker = false },
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "자습 장소",
                    style = DodamTheme.typography.heading2Bold(),
                    color = DodamTheme.colors.labelNormal,
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
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
                                Text(
                                    text = it.place,
                                    color = DodamTheme.colors.labelAssistive,
                                    style = DodamTheme.typography.headlineMedium(),
                                )
                            },
                            content = {
                                if (it == nightStudyPlace) {
                                    Icon(
                                        imageVector = DodamIcons.Checkmark.value,
                                        contentDescription = "체크마크",
                                        tint = DodamTheme.colors.primaryNormal,
                                    )
                                }
                            },
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(DodamTheme.colors.backgroundNeutral)
            .addFocusCleaner(focusManager),
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "심야 자습 신청하기",
                onBackClick = popBackStack,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
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
                    modifier = Modifier,
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DodamTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = nightStudyReason,
                        onValueChange = { nightStudyReason = it },
                        label = "심야 자습 사유",
                        isError = nightStudyReason.length !in 10..250 && uiState.message.isNotBlank(),
                        supportText = if (nightStudyReason.length !in 10..250) "사유를 10자 이상 입력해주세요." else "",
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                AskNightStudyCard(
                    text = "자습 장소",
                    action = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                text = nightStudyPlace.place,
                                style = DodamTheme.typography.headlineRegular(),
                                color = DodamTheme.colors.primaryNormal,
                            )
                            Icon(
                                imageVector = UpDownArrow,
                                contentDescription = "위아래 화살표",
                                tint = DodamTheme.colors.primaryNormal,
                            )
                        }
                    },
                    onClick = {
                        showPlacePicker = true
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                AskNightStudyCard(
                    text = "시작 날짜",
                    action = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Text(
                                text = nightStudyStartDate.format(DateTimeFormatter.ofPattern("MM월 dd일")),
                                style = DodamTheme.typography.headlineRegular(),
                                color = DodamTheme.colors.primaryNormal,
                            )
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = DodamIcons.Calendar.value,
                                contentDescription = "달력 아이콘",
                                tint = DodamTheme.colors.primaryNormal,
                            )
                        }
                    },
                    onClick = {
                        datePickerState.selectedDate = CalendarDate(
                            year = nightStudyStartDate.year,
                            month = nightStudyStartDate.monthValue,
                            dayOfMonth = nightStudyStartDate.dayOfMonth,
                            utcTimeMillis = nightStudyStartDate.toKotlinLocalDate().utcTimeMill,
                        )
                        showDatePicker = Pair(true, "시작")
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                AskNightStudyCard(
                    text = "종료 날짜",
                    action = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Text(
                                text = nightStudyEndDate.format(DateTimeFormatter.ofPattern("MM월 dd일")),
                                style = DodamTheme.typography.headlineRegular(),
                                color = DodamTheme.colors.primaryNormal,
                            )
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = DodamIcons.Calendar.value,
                                contentDescription = "달력 아이콘",
                                tint = DodamTheme.colors.primaryNormal,
                            )
                        }
                    },
                    onClick = {
                        datePickerState.selectedDate = CalendarDate(
                            year = nightStudyEndDate.year,
                            month = nightStudyEndDate.monthValue,
                            dayOfMonth = nightStudyEndDate.dayOfMonth,
                            utcTimeMillis = nightStudyEndDate.toKotlinLocalDate().utcTimeMill,
                        )
                        showDatePicker = Pair(true, "종료")
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                AskNightStudyCard(
                    text = "휴대폰 사용",
                    action = {
                        DodamCheckBox(
                            onClick = { doNeedPhone = !doNeedPhone },
                            checked = doNeedPhone,
                        )
                    },
                    onClick = {
                        doNeedPhone = !doNeedPhone
                    },
                )

                AnimatedVisibility(doNeedPhone) {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        DodamTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = reasonForPhone,
                            onValueChange = { reasonForPhone = it },
                            label = "휴대폰 사용 사유",
                        )
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }

            DodamButton(
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
                    .align(Alignment.BottomCenter),
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
                enabled = (nightStudyReason.length >= 10 && nightStudyStartDate < nightStudyEndDate) && !uiState.isLoading,
                text = "신청",
                loading = uiState.isLoading,
            )
        }
    }
}

@Composable
private fun AskNightStudyCard(modifier: Modifier = Modifier, text: String, action: @Composable () -> Unit, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .clickable(
                onClick = onClick,
                indication = rememberBounceIndication(),
                interactionSource = remember { MutableInteractionSource() },
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = DodamTheme.colors.labelAlternative,
            style = DodamTheme.typography.headlineMedium(),
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier.padding(vertical = 8.dp),
        ) {
            action()
        }
    }
}
