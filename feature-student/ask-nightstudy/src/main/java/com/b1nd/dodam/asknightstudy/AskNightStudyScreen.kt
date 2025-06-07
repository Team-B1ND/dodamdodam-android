package com.b1nd.dodam.asknightstudy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.b1nd.dodam.common.date.DodamDate
import com.b1nd.dodam.common.utiles.utcTimeMill
import com.b1nd.dodam.data.core.model.NightStudyType
import com.b1nd.dodam.data.core.model.Place
import com.b1nd.dodam.data.core.model.ProjectNightStudyType
import com.b1nd.dodam.data.core.model.ProjectPlace
import com.b1nd.dodam.data.core.model.toNightStudyType
import com.b1nd.dodam.data.core.model.toProjectNightStudyType
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.AvatarSize
import com.b1nd.dodam.designsystem.component.CalendarDate
import com.b1nd.dodam.designsystem.component.DodamAvatar
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamCheckBox
import com.b1nd.dodam.designsystem.component.DodamDatePickerBottomSheet
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.designsystem.component.DodamEmpty
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.rememberDodamDatePickerState
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.component.InputField
import com.b1nd.dodam.ui.icons.UpDownArrow
import com.b1nd.dodam.ui.util.addFocusCleaner
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toKotlinLocalDate
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
internal fun AskNightStudyScreen(
    viewModel: AskNightStudyViewModel = koinViewModel(),
    popBackStack: () -> Unit,
    showToast: (String, String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    var nightStudyReason by remember { mutableStateOf("") }
    var projectNightStudyReason by remember { mutableStateOf("") }
    var projectOverview by remember { mutableStateOf("") }

    var nightStudyStartDate by remember { mutableStateOf(LocalDate.now()) }
    var nightStudyEndDate by remember { mutableStateOf(LocalDate.now().plusDays(13)) }

    val projectNightStudyTypeList = persistentListOf("심자 1", "심자 2")
    val nightStudyTypeList = persistentListOf("심자 1", "심자 2", "심자 3")
    val projectNightStudyMembers = remember { mutableStateListOf<Long>() }

    var projectNightStudyType by remember { mutableStateOf(ProjectNightStudyType.NIGHT_STUDY_PROJECT_2) }
    var nightStudyType by remember { mutableStateOf(NightStudyType.NIGHT_STUDY_2) }

    val nightStudyPlace by remember { mutableStateOf(Place.PROJECT5) }
    var projectNightStudyPlace by remember { mutableStateOf(ProjectPlace.LAB12) }

    var showDatePicker by remember { mutableStateOf(Pair(false, "시작")) }
    var showPlacePicker by remember { mutableStateOf(Pair(false, "시각")) }

    var doNeedPhone by remember { mutableStateOf(false) }
    var reasonForPhone by remember { mutableStateOf("") }

    var searchStudent by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDodamDatePickerState()
    val nowDate = DodamDate.localDateNow()

    val focusManager = LocalFocusManager.current

    var nightTypeIndex by remember { mutableIntStateOf(0) }

    val nightTypeList = persistentListOf(
        "개인",
        "프로젝트",
    )

    val nightTypeItem = List(2) { index ->
        DodamSegment(
            selected = nightTypeIndex == index,
            text = nightTypeList[index],
            onClick = { nightTypeIndex = index },
        )
    }.toImmutableList()

    LaunchedEffect(Unit) {
        viewModel.getNightStudyStudent()
    }

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

                val days = nightStudyStartDate.toKotlinLocalDate().daysUntil(kotlinDate)
                days in 0..13
            },
            onClickDate = { date, isValid ->
                if (!isValid) return@DodamDatePickerBottomSheet

                val localDate = LocalDate.of(date.year, date.month, date.dayOfMonth)
                if (showDatePicker.second == "시작") {
                    nightStudyStartDate = localDate

                    val days = nightStudyStartDate.toKotlinLocalDate()
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

    if (showPlacePicker.first) {
        ModalBottomSheet(
            modifier = Modifier.navigationBarsPadding(),
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            onDismissRequest = { showPlacePicker = Pair(false, "") },
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = if (showPlacePicker.second.isPlace()) "자습 장소" else "진행 시각",
                    style = DodamTheme.typography.heading2Bold(),
                    color = DodamTheme.colors.labelNormal,
                )

                if (showPlacePicker.second.isPlace()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(
                            items = ProjectPlace.entries,
                            key = { it.name },
                        ) {
                            InputField(
                                onClick = {
                                    projectNightStudyPlace = it
                                },
                                text = {
                                    Text(
                                        text = it.place,
                                        color = DodamTheme.colors.labelAssistive,
                                        style = DodamTheme.typography.headlineMedium(),
                                    )
                                },
                                content = {
                                    if (it == projectNightStudyPlace) {
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
                } else {
                    if (nightTypeIndex.isProject()){
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            items(
                                items = projectNightStudyTypeList,
                                key = { it },
                            ) {
                                InputField(
                                    onClick = {
                                        projectNightStudyType = it.toProjectNightStudyType()
                                    },
                                    text = {
                                        Text(
                                            text = it,
                                            color = DodamTheme.colors.labelAssistive,
                                            style = DodamTheme.typography.headlineMedium(),
                                        )
                                    },
                                    content = {
                                        if (it.toProjectNightStudyType() == projectNightStudyType) {
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
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            items(
                                items = nightStudyTypeList,
                                key = { it },
                            ) {
                                InputField(
                                    onClick = {
                                        nightStudyType = it.toNightStudyType()
                                    },
                                    text = {
                                        Text(
                                            text = it,
                                            color = DodamTheme.colors.labelAssistive,
                                            style = DodamTheme.typography.headlineMedium(),
                                        )
                                    },
                                    content = {
                                        if (it.toNightStudyType() == nightStudyType) {
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
                DodamSegmentedButton(
                    segments = nightTypeItem,
                )
                Column(
                    modifier = Modifier,
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DodamTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = if (nightTypeIndex.isProject()) projectNightStudyReason else nightStudyReason,
                        onValueChange = {
                            if (nightTypeIndex.isProject()) {
                                projectNightStudyReason =
                                    it
                            } else {
                                nightStudyReason = it
                            }
                        },
                        label = if (nightTypeIndex.isProject()) "프로젝트 명" else "심야 자습 사유",
                        isError = nightStudyReason.length !in 10..250 && uiState.message.isNotBlank(),
                        supportText = when {
                            nightTypeIndex.isProject() && projectNightStudyReason.length !in 1..250 ->
                                "프로젝트 이름을 입력해주세요"

                            !nightTypeIndex.isProject() && nightStudyReason.length !in 10..250 ->
                                "사유를 10자 이상 입력해주세요."

                            else -> ""
                        }
                    )
                    if (nightTypeIndex.isProject()) {
                        Spacer(modifier = Modifier.height(20.dp))
                        DodamTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = projectOverview,
                            onValueChange = { projectOverview = it },
                            label = "프로젝트 개요",
                            isError = projectOverview.length !in 10..250 && uiState.message.isNotBlank(),
                            supportText = if (projectOverview.length !in 10..250) "개요를 10자 이상 입력해주세요." else "",
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                AskNightStudyCard(
                    text = "진행 시각",
                    action = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                text = if(nightTypeIndex.isProject())projectNightStudyType.type else nightStudyType.type,
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
                        showPlacePicker = Pair(true, "시각")
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
                if (nightTypeIndex.isProject()) {
                    Column {
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            DodamTextField(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 24.dp),
                                value = searchStudent,
                                onValueChange = { searchStudent = it },
                                label = "학생 검색",
                                onClickRemoveRequest = { searchStudent = "" },
                            )
                            Image(
                                modifier = Modifier.size(24.dp),
                                imageVector = DodamIcons.MagnifyingGlass.value,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(DodamTheme.colors.labelAssistive),
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        val filteredStudentList = uiState.students.let { list ->
                            if (searchStudent.isNotEmpty()) {
                                list.filter { it.name.contains(searchStudent) }
                            } else {
                                list
                            }
                        }.sortedByDescending { it.id in projectNightStudyMembers }
                        LazyColumn(
                            modifier = Modifier.height(200.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(
                                items = filteredStudentList,
                            ) {
                                DodamNightStudyMemberComponent(
                                    modifier = Modifier.clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberBounceIndication(),
                                        onClick = {
                                            if (!it.isBanned) {
                                                if (it.id in projectNightStudyMembers) {
                                                    projectNightStudyMembers.remove(it.id)
                                                } else {
                                                    projectNightStudyMembers.add(it.id)
                                                }
                                            }
                                        },
                                    ),
                                    name = it.name,
                                    grade = it.grade,
                                    room = it.room,
                                    image = it.profileImage,
                                    isInclude = it.id in projectNightStudyMembers,
                                    isBan = it.isBanned,
                                )
                            }

                            item {
                                if (filteredStudentList.isEmpty()) {
                                    DodamEmpty(
                                        onClick = {},
                                        title = "검색결과가 없어요!",
                                        buttonText = "학생 이름을 잘 작성해주세요",
                                    )
                                }
                            }
                        }
                    }
                } else {
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
                    if (nightTypeIndex.isProject()) {
                        viewModel.askProjectNightStudy(
                            type = projectNightStudyType,
                            name = projectNightStudyReason,
                            description = projectOverview,
                            startAt = nightStudyStartDate.toKotlinLocalDate(),
                            endAt = nightStudyEndDate.toKotlinLocalDate(),
                            students = projectNightStudyMembers.map { it.toInt() },
                        )
                    } else {
                        viewModel.askNightStudy(
                            content = nightStudyReason,
                            type = nightStudyType,
                            doNeedPhone = doNeedPhone,
                            reasonForPhone = if (doNeedPhone) reasonForPhone else null,
                            startAt = nightStudyStartDate.toKotlinLocalDate(),
                            endAt = nightStudyEndDate.toKotlinLocalDate(),
                        )
                    }
                },
                enabled = if (nightTypeIndex.isProject()) {
                    (
                            projectNightStudyReason.isNotEmpty() && nightStudyStartDate
                                    < nightStudyEndDate && projectOverview.length >= 10
                            ) && !uiState.isLoading
                } else {
                    (nightStudyReason.length >= 10 && nightStudyStartDate < nightStudyEndDate) && !uiState.isLoading
                },
                text = "신청",
                loading = uiState.isLoading,
            )
        }
    }
}

private fun Int.isProject() = this == 1

private fun String.isPlace() = this == "장소"

@Composable
private fun AskNightStudyCard(
    modifier: Modifier = Modifier,
    text: String,
    action: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.clickable(
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

@Composable
private fun DodamNightStudyMemberComponent(
    modifier: Modifier = Modifier,
    image: String? = "",
    name: String,
    grade: Int,
    room: Int,
    isInclude: Boolean,
    isBan: Boolean,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DodamAvatar(
            model = image,
            avatarSize = AvatarSize.Large,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(
                        text = name,
                        style = DodamTheme.typography.body1Medium(),
                        color = DodamTheme.colors.labelNormal,
                    )
                    Text(
                        text = "$grade-$room",
                        style = DodamTheme.typography.body2Medium(),
                        color = DodamTheme.colors.labelAlternative,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                if (isInclude) {
                    Image(
                        modifier = Modifier.size(22.dp),
                        imageVector = DodamIcons.CheckmarkCircleFilled.value,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(DodamTheme.colors.primaryNormal),
                    )
                }
                if (isBan) {
                    Image(
                        modifier = Modifier.size(22.dp),
                        imageVector = DodamIcons.ColorXMark.value,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
