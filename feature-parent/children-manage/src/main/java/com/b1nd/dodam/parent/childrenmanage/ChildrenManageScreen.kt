package com.b1nd.dodam.parent.childrenmanage

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.b1nd.dodam.data.core.model.Children
import com.b1nd.dodam.dds.animation.bounceClick
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.AvatarSize
import com.b1nd.dodam.designsystem.component.DodamAvatar
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamCheckBox
import com.b1nd.dodam.designsystem.component.DodamTextButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TextButtonType
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.parent.childrenmanage.model.ChildrenSideEffect
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun ChildrenManageScreen(
    viewModel: ChildrenManageViewModel = koinViewModel(),
    popBackStack: () -> Unit,
    changeBottomNavVisible: (visible: Boolean) -> Unit,
    navigateToInfo: (list: List<Children>)->Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var code by remember { mutableStateOf("") }
    var etcRelation by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val bottomSheetMaxHeight = screenHeight * 0.8f
    val relations = listOf("부", "모", "조부", "조모", "기타")
    var selectedRelation by remember { mutableStateOf<String?>(null) }
    val uiState by viewModel.uiState.collectAsState()
    var showError by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        changeBottomNavVisible(false)
    }

    DisposableEffect(true) {
        onDispose {
            changeBottomNavVisible(true)
        }
    }
    LaunchedEffect(true) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ChildrenSideEffect.SuccessGetChildren -> {
                    showBottomSheet = false
                    showError = false
                }
                is ChildrenSideEffect.Failed -> {
                    showError = true
                }
            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            windowInsets = WindowInsets.ime,
            onDismissRequest = {
                showBottomSheet = false
            },
            modifier = Modifier
                .navigationBarsPadding()
                .heightIn(max = bottomSheetMaxHeight),
            containerColor = DodamTheme.colors.backgroundNormal,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    },
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                "자녀 등록하기",
                                color = DodamTheme.colors.labelNormal,
                                style = DodamTheme.typography.heading2Bold(),
                            )
                            DodamTextButton(
                                onClick = {
                                    if (selectedRelation != null && (selectedRelation != "기타" || etcRelation.isNotBlank())) {
                                        val relationToSend = if (selectedRelation == "기타") etcRelation else selectedRelation
                                        viewModel.getChildren(code = code, relation = relationToSend ?: "")
                                    }
                                },
                                text = "등록",
                                type = TextButtonType.Primary,
                            )
                        }
                        Text(
                            "자녀의 앱에서 '전체 > 내 학생 코드 보기' 탭에서 확인할 수 있어요",
                            color = DodamTheme.colors.labelAlternative,
                            style = DodamTheme.typography.body1Medium(),
                        )
                    }
                    DodamTextField(
                        value = code,
                        onValueChange = {
                            code = it
                        },
                        label = "학생 코드",
                        onClickRemoveRequest = {
                            code = ""
                        },
                        isError = showError,
                        supportText = if (showError) "학생을 찾을 수 없습니다." else "",
                    )
                    Spacer(Modifier.height(12.dp))
                    Column {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                "학생과의 관계",
                                color = DodamTheme.colors.labelAssistive,
                                style = DodamTheme.typography.labelMedium(),
                            )
                            relations.forEach { relation ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = relation,
                                        color = DodamTheme.colors.labelAlternative,
                                        style = DodamTheme.typography.headlineMedium(),
                                    )
                                    DodamCheckBox(
                                        modifier = Modifier.padding(8.dp),
                                        onClick = {
                                            selectedRelation =
                                                if (selectedRelation == relation) null else relation
                                        },
                                        checked = selectedRelation == relation,
                                    )
                                }
                            }
                            if (selectedRelation == "기타") {
                                DodamTextField(
                                    value = etcRelation,
                                    onValueChange = {
                                        etcRelation = it
                                    },
                                    label = "",
                                    onClickRemoveRequest = {
                                        etcRelation = ""
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "학생코드로\n자녀를 등록해주세요",
                onBackClick = popBackStack,
                type = TopAppBarType.Large,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) { it ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                items(uiState.chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        rowItems.fastForEach { (name, relation) ->
                            ChildrenCard(
                                name = name,
                                relation = relation,
                                modifier = Modifier.weight(1f),
                            )
                        }
                        if (rowItems.size == 1 && rowItems.first() == uiState.last()) {
                            AddChildrenButton(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    showBottomSheet = true
                                },
                            )
                        }
                    }
                }
                if (uiState.size % 2 != 1) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            AddChildrenButton(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    showBottomSheet = true
                                },
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
                item {
                    Spacer(Modifier.height(150.dp))
                }
            }

            DodamButton(
                onClick = {
                    navigateToInfo(
                        uiState
                    )
                },
                text = "완료",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .padding(horizontal = 16.dp),
            )
        }
    }
}

@Composable
fun ChildrenCard(modifier: Modifier = Modifier, profile: String? = null, name: String, relation: String) {
    Box(
        modifier = modifier
            .background(
                color = DodamTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(12.dp),
            )
            .fillMaxWidth()
            .height(144.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center),
        ) {
            DodamAvatar(
                avatarSize = AvatarSize.ExtraLarge,
                contentDescription = "프로필 이미지",
                model = profile,
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = name,
                    color = DodamTheme.colors.labelStrong,
                    style = DodamTheme.typography.headlineBold(),
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "학생과의 관계: ",
                        color = DodamTheme.colors.labelAssistive,
                        style = DodamTheme.typography.labelRegular(),
                    )
                    Text(
                        text = relation,
                        color = DodamTheme.colors.labelAssistive,
                        style = DodamTheme.typography.labelRegular(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Composable
fun AddChildrenButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .background(
                color = DodamTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(12.dp),
            )
            .fillMaxWidth()
            .height(144.dp)
            .bounceClick(
                onClick = onClick,
                interactionColor = DodamTheme.colors.lineNormal,
            ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center),
        ) {
            Icon(
                imageVector = DodamIcons.Plus.value,
                contentDescription = null,
                tint = DodamTheme.colors.labelAssistive,
                modifier = Modifier.size(24.dp),
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "자녀 추가 등록",
                color = DodamTheme.colors.labelAssistive,
                style = DodamTheme.typography.labelBold(),
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Column {
        ChildrenCard(
            name = "한준혁",
            relation = "본좌",
            profile = "https://dodamdodam-storage.s3.ap-northeast-2.amazonaws.com/dodamdodam-storage/431b87b3-ff1b-4079-b070-d4a6b44665d8IMG_0007",
        )
        Spacer(Modifier.height(16.dp))
        AddChildrenButton(
            onClick = {},
        )
    }
}
