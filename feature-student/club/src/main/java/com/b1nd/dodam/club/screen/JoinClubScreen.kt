package com.b1nd.dodam.club.screen

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.b1nd.dodam.club.MyClubViewModel
import com.b1nd.dodam.club.R
import com.b1nd.dodam.club.model.ClubState
import com.b1nd.dodam.club.model.JoinedClubUiState
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamButtonDialog
import com.b1nd.dodam.designsystem.component.DodamModalBottomSheet
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.component.CustomFullIconButton
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun JoinClubScreen(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
    viewModel: MyClubViewModel = koinViewModel(),
    onNavigateToJoin: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getAllClub()
    }

    val context = LocalContext.current

    var titleIndex by remember { mutableIntStateOf(0) }
    var showClubPicker by remember { mutableStateOf(false) }
    var showEmptyClub by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val state by viewModel.state.collectAsState()

    val showBottomSheet = remember { mutableStateOf(false) }
    val showSelfBottomSheet = remember { mutableStateOf(false) }

    var allClubList = state.allClubList.filter { it.state == ClubState.ALLOWED }
    var allSelfClubList = state.allSelfClubList

    when (state.joinedClubUiState) {
        is JoinedClubUiState.Success -> {
            allClubList = state.allClubList
            allSelfClubList = state.allSelfClubList
        }

        else -> {
        }
    }

    val text: ImmutableList<String> = listOf(
        "창체",
        "자율",
    ).toImmutableList()

    val introduceList: ImmutableList<String> = listOf(
        "창체는 자기소개가 없습니다.",
        "창체는 자기소개가 없습니다.",
        "창체는 자기소개가 없습니다.",
    ).toImmutableList()

    val clickedNum = remember { mutableIntStateOf(1) }

    val allClubNameList = allClubList.filter { it.state == ClubState.ALLOWED }.map { it.name }
    val selectedClubs = remember { mutableStateOf(setOf<String>()) }

    val allSelfClubNameList = allSelfClubList
        .filter { it.state == ClubState.ALLOWED }
        .map { it.name }
        .toImmutableList()

    val selectedSelfClubs = remember {
        mutableStateOf<ImmutableSet<String>>(persistentSetOf())
    }

    val filteredClubList = allClubNameList.filter { it !in selectedClubs.value }
    val filteredSelfClubList = allSelfClubNameList.filter { it !in selectedSelfClubs.value }

    val firstClub = remember { mutableStateOf("") }
    val secondClub = remember { mutableStateOf("") }
    val thirdClub = remember { mutableStateOf("") }

    val clubIntroduces = remember { mutableStateMapOf<String, String>() }

    val clubIdList = remember { mutableStateListOf<Int>() }

    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(selectedClubs.value) {
        clubIdList.clear()

        val orderedSelectedClubs = listOf(firstClub.value, secondClub.value, thirdClub.value)
            .filter { it.isNotEmpty() }

        clubIdList.addAll(
            orderedSelectedClubs.mapNotNull { clubName ->
                allClubList.find { it.name == clubName }?.id
            },
        )
    }

    val selfClubIdList = remember { mutableStateListOf<Int>() }
    val selfIntroduceList = remember { mutableStateListOf<String>() }

    LaunchedEffect(selectedSelfClubs.value) {
        selfClubIdList.clear()
        selfIntroduceList.clear()

        val selectedSelfClubNames = selectedSelfClubs.value.toList()

        selectedSelfClubNames.forEach { clubName ->
            allSelfClubList.find { it.name == clubName }?.id?.let { id ->
                selfClubIdList.add(id)

                val nonEmptyIntroductions = selectedSelfClubNames
                    .filter { clubName -> clubIntroduces.getOrDefault(clubName, "").isNotEmpty() }
                    .map { clubName -> clubIntroduces.getOrDefault(clubName, "") }

                selfIntroduceList.addAll(nonEmptyIntroductions)
            }
        }
    }

    if (showSelfBottomSheet.value) {
        DodamModalBottomSheet(
            onDismissRequest = {
                showSelfBottomSheet.value = false
            },
            title = {
                Text(
                    text = "자율 동아리를 선택해 주세요",
                    style = DodamTheme.typography.heading2Bold(),
                    color = DodamTheme.colors.labelNormal,
                )
                Spacer(Modifier.height(8.dp))
            },
            content = {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(color = DodamTheme.colors.backgroundNormal),
                ) {
                    LazyColumn {
                        itemsIndexed(filteredSelfClubList) { _, item ->
                            Box(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        selectedSelfClubs.value = (selectedSelfClubs.value + item).toImmutableSet()
                                        clubIntroduces[item] = ""
                                        showSelfBottomSheet.value = false
                                    },
                            ) {
                                Text(
                                    text = item,
                                    color = DodamTheme.colors.labelAssistive,
                                    style = DodamTheme.typography.body2Bold(),
                                )
                            }
                            Spacer(modifier = modifier.height(16.dp))
                        }
                    }
                }
            },
        )
    }
    if (showBottomSheet.value) {
        DodamModalBottomSheet(
            onDismissRequest = {
                showBottomSheet.value = false
            },
            title = {
                Text(
                    text = "${clickedNum.intValue}지망 동아리를\n선택해 주세요",
                    style = DodamTheme.typography.heading2Bold(),
                    color = DodamTheme.colors.labelNormal,
                )
                Spacer(Modifier.height(8.dp))
            },
            content = {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(DodamTheme.colors.backgroundNormal),
                ) {
                    filteredClubList.forEachIndexed { _, item ->

                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    when (clickedNum.intValue) {
                                        1 -> {
                                            firstClub.value = item
                                        }

                                        2 -> {
                                            secondClub.value = item
                                        }

                                        3 -> {
                                            thirdClub.value = item
                                        }
                                    }

                                    selectedClubs.value = setOf(
                                        firstClub.value,
                                        secondClub.value,
                                        thirdClub.value,
                                    ).filter { it.isNotEmpty() }.toSet()

                                    showBottomSheet.value = false
                                },
                        ) {
                            Text(
                                text = item,
                                color = DodamTheme.colors.labelAssistive,
                                fontSize = 16.sp,
                            )
                        }
                        Spacer(modifier = modifier.height(16.dp))
                    }
                }
            },
        )
    }

    if (showClubPicker) {
        Dialog(
            onDismissRequest = { showClubPicker = false },
        ) {
            DodamButtonDialog(
                confirmButton = {
                    viewModel.applyClub(
                        clubId = clubIdList,
                        introduce = introduceList,
                        selfClubId = selfClubIdList,
                        selfIntroduce = selfIntroduceList,
                    )
                    onNavigateToJoin()
                    showClubPicker = false
                },
                confirmButtonText = "신청 완료하기",
                confirmButtonRole = ButtonRole.Primary,
                dismissButton = {
                    showClubPicker = false
                },
                dismissButtonText = "취소",
                dismissButtonRole = ButtonRole.Assistive,
                title = "정말 확실합니까?",
                body = buildString {
                    append("창체동아리 : \n")
                    append(selectedClubs.value.joinToString(",\n") { it })
                    append("\n\n자율동아리 : \n")
                    if (selectedSelfClubs.value.isNotEmpty()) {
                        append(selectedSelfClubs.value.joinToString(",\n") { it })
                    } else {
                        append("선택된 자율동아리가 없습니다.")
                    }
                    append("\n위 동아리로 신청을 넣겠습니까?")
                },
            )
        }
    }

    if (showEmptyClub) {
        Dialog(
            onDismissRequest = { showEmptyClub = false },
        ) {
            DodamButtonDialog(
                confirmButton = {
                    showEmptyClub = false
                    showClubPicker = true
                },
                confirmButtonText = "네",
                dismissButton = {
                    showEmptyClub = false
                },
                dismissButtonText = "취소",
                dismissButtonRole = ButtonRole.Assistive,
                title = "정말 확실합니까?",
                body = "자율동아리 신청이 비었어요.\n창체만 신청 하시겠습니까?",
            )
        }
    }

    val item = List(2) { index: Int ->
        DodamSegment(
            selected = titleIndex == index,
            text = text[index],
            onClick = {
                titleIndex = index
            },
        )
    }.toImmutableList()

    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "동아리 신청",
                onBackClick = popBackStack,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp, start = 16.dp, top = 16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                DodamSegmentedButton(
                    segments = item,
                )
                if (titleIndex == 1) {
                    selectedSelfClubs.value.forEachIndexed { index, clubName ->
                        ClubCard(
                            introduce = clubIntroduces.getOrDefault(clubName, ""),
                            onRemoveClick = {
                                clubIntroduces.remove(clubName)
                                selectedSelfClubs.value = (selectedSelfClubs.value - clubName).toImmutableSet()
                            },
                            onIntroduceChange = { newIntroduce ->
                                clubIntroduces[clubName] = newIntroduce
                                if (index < selfIntroduceList.size) {
                                    selfIntroduceList[index] = newIntroduce
                                } else {
                                    selfIntroduceList.add(newIntroduce)
                                }
                            },
                            clubName = clubName,
                        )
                    }
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(
                                color = DodamTheme.colors.backgroundNormal,
                                shape = RoundedCornerShape(12.dp),
                            ),
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth(),
                        ) {
                            CustomFullIconButton(
                                onClick = {
                                    showSelfBottomSheet.value = true
                                },
                                icon = DodamIcons.Plus,
                                modifier = modifier
                                    .fillMaxWidth(),
                            )
                        }
                    }
                    Spacer(Modifier.height(50.dp))
                } else {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(
                                color = DodamTheme.colors.backgroundNormal,
                                shape = RoundedCornerShape(12.dp),
                            )
                            .padding(horizontal = 16.dp, vertical = 18.dp),
                    ) {
                        Row {
                            Text(
                                text = "1순위 :",
                                style = DodamTheme.typography.headlineBold(),
                                color = DodamTheme.colors.labelNormal,
                            )
                            Spacer(Modifier.width(15.dp))
                            Row(
                                Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberBounceIndication(),
                                    onClick = {
                                        showBottomSheet.value = true
                                        clickedNum.intValue = 1
                                    },
                                ),
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = firstClub.value.ifEmpty { "선택해 주세요" },
                                    color = DodamTheme.colors.primaryNormal,
                                    style = DodamTheme.typography.body1Medium(),
                                )
                                Spacer(Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(R.drawable.frame),
                                    contentDescription = null,
                                )
                            }
                        }

                        Spacer(Modifier.height(28.dp))

                        Row {
                            Text(
                                text = "2순위 :",
                                style = DodamTheme.typography.headlineBold(),
                                color = DodamTheme.colors.labelNormal,
                            )
                            Spacer(Modifier.width(15.dp))
                            Row(
                                Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberBounceIndication(),
                                    onClick = {
                                        showBottomSheet.value = true
                                        clickedNum.intValue = 2
                                    },
                                ),
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = secondClub.value.ifEmpty { "선택해 주세요" },
                                    color = DodamTheme.colors.primaryNormal,
                                    style = DodamTheme.typography.body1Medium(),
                                )
                                Spacer(Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(R.drawable.frame),
                                    contentDescription = null,
                                )
                            }
                        }

                        Spacer(Modifier.height(28.dp))

                        Row {
                            Text(
                                text = "3순위: ",
                                style = DodamTheme.typography.headlineBold(),
                                color = DodamTheme.colors.labelNormal,
                            )
                            Spacer(Modifier.width(15.dp))
                            Row(
                                Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberBounceIndication(),
                                    onClick = {
                                        showBottomSheet.value = true
                                        clickedNum.intValue = 3
                                    },
                                ),
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = thirdClub.value.ifEmpty { "선택해 주세요" },
                                    color = DodamTheme.colors.primaryNormal,
                                    style = DodamTheme.typography.body1Medium(),
                                )
                                Spacer(Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(R.drawable.frame),
                                    contentDescription = null,
                                )
                            }
                        }

                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
            DodamButton(
                onClick = {
                    if (selfClubIdList.isEmpty()) {
                        showEmptyClub = true
                    } else {
                        showClubPicker = true
                    }
                },
                text = "동아리 입부 신청하기",
                enabled = selectedClubs.value.size == 3,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 10.dp)
                    .align(alignment = Alignment.BottomCenter),
            )
        }
    }
}

@Composable
fun ClubCard(modifier: Modifier = Modifier, introduce: String, clubName: String, onIntroduceChange: (String) -> Unit, onRemoveClick: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DodamTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(16.dp),
    ) {
        Row {
            Text(
                text = "자율동아리 : ",
                style = DodamTheme.typography.headlineBold(),
                color = DodamTheme.colors.labelNormal,
            )
            Text(
                text = clubName,
                style = DodamTheme.typography.headlineBold(),
                color = DodamTheme.colors.primaryNormal,
            )
        }
        Spacer(Modifier.height(14.dp))
        DodamTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = introduce,
            onValueChange = { newIntroduce ->
                if (newIntroduce.length <= 300) {
                    onIntroduceChange(newIntroduce)
                }
            },
            singleLine = false,
            minLines = 6,
            maxLines = 8,
            label = "자기소개",
            onClickRemoveRequest = onRemoveClick,
        )
        Spacer(Modifier.height(7.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = introduce.length.toString(),
                color = DodamTheme.colors.primaryNormal,
                style = DodamTheme.typography.body1Medium(),
            )
            Text(
                text = "/300",
                color = DodamTheme.colors.labelAssistive,
                style = DodamTheme.typography.body1Medium(),
            )
        }
    }
}
