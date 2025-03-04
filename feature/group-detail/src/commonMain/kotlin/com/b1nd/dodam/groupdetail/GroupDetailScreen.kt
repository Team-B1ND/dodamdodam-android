package com.b1nd.dodam.groupdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.b1nd.dodam.data.core.model.getName
import com.b1nd.dodam.data.division.model.DivisionMember
import com.b1nd.dodam.data.division.model.DivisionPermission
import com.b1nd.dodam.data.division.model.isAdmin
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDivider
import com.b1nd.dodam.designsystem.component.DodamModalBottomSheet
import com.b1nd.dodam.designsystem.component.DodamTag
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TagType
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.groupdetail.model.GroupDetailSideEffect
import com.b1nd.dodam.groupdetail.viewmodel.GroupDetailViewModel
import com.b1nd.dodam.ui.component.DodamGroupMemberCard
import com.b1nd.dodam.ui.component.DodamMenuDialog
import com.b1nd.dodam.ui.component.DodamMenuItem
import com.b1nd.dodam.ui.component.DodamMenuItemColor
import com.b1nd.dodam.ui.component.SnackbarState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GroupDetailScreen(
    viewModel: GroupDetailViewModel = koinViewModel(),
    id: Int,
    showSnackbar: (state: SnackbarState, message: String) -> Unit,
    popBackStack: () -> Unit,
    navigateToGroupAdd: (id: Int) -> Unit,
    navigateToGroupWaiting: (id: Int, name: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val isJoin = uiState.divisionInfo?.myPermission != null || uiState.isLoading
    val isPermission = uiState.divisionInfo?.myPermission?.isAdmin() ?: false
    var bottomSheetUser: DivisionMember? by remember { mutableStateOf(null) }
    var isShowMemberBottomSheet by remember { mutableStateOf(false) }

    var dialogMemberUser: DivisionMember? by remember { mutableStateOf(null) }
    var isShowMemberDialog by remember { mutableStateOf(false) }

    var isShowGroupDialog by remember { mutableStateOf(false) }

    val onClickMember: (DivisionMember) -> Unit = {
        bottomSheetUser = it
        isShowMemberBottomSheet = true
    }

    LaunchedEffect(true) {
        viewModel.load(id = id)
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is GroupDetailSideEffect.FailedEditPermission -> {
                    showSnackbar(SnackbarState.ERROR, sideEffect.throwable.message ?: "Error")
                }
                GroupDetailSideEffect.SuccessEditPermission -> {
                    showSnackbar(SnackbarState.SUCCESS, "성공적으로 멤버의 권환을 수정했습니다!")
                }

                is GroupDetailSideEffect.FailedDeleteGroup -> {
                    showSnackbar(SnackbarState.ERROR, sideEffect.throwable.message ?: "Error")
                }
                GroupDetailSideEffect.SuccessDeleteGroup -> {
                    showSnackbar(SnackbarState.SUCCESS, "성공적으로 그룹을 삭제했습니다.")
                    popBackStack()
                }
                is GroupDetailSideEffect.FailedKickMember -> {
                    showSnackbar(SnackbarState.ERROR, sideEffect.throwable.message ?: "멤버 추방을 실패했습니다.")
                }
            }
        }
    }

    if (isShowMemberDialog && dialogMemberUser != null) {
        BasicAlertDialog(
            onDismissRequest = {
                isShowMemberDialog = false
                dialogMemberUser = null
            },
            content = {
                val dialogItems = mutableListOf<DodamMenuItem>().apply {
                    if (dialogMemberUser!!.permission != DivisionPermission.ADMIN) {
                        add(
                            DodamMenuItem(
                                item = "승격하기",
                                color = DodamMenuItemColor.Normal,
                                onClickItem = {
                                    viewModel.upperPermission(
                                        divisionId = id,
                                        divisionMember = dialogMemberUser!!,
                                    )
                                    isShowMemberDialog = false
                                    dialogMemberUser = null
                                    isShowMemberBottomSheet = false
                                    bottomSheetUser = null
                                },
                            ),
                        )
                    }

                    if (dialogMemberUser!!.permission != DivisionPermission.READER) {
                        add(
                            DodamMenuItem(
                                item = "강등하기",
                                color = DodamMenuItemColor.Negative,
                                onClickItem = {
                                    viewModel.lowerPermission(
                                        divisionId = id,
                                        divisionMember = dialogMemberUser!!,
                                    )
                                    isShowMemberDialog = false
                                    dialogMemberUser = null
                                    isShowMemberBottomSheet = false
                                    bottomSheetUser = null
                                },
                            ),
                        )
                    }
                }
                DodamMenuDialog(
                    items = dialogItems.toImmutableList(),
                )
            },
        )
    }

    if (isShowGroupDialog) {
        Dialog(
            onDismissRequest = {
                isShowGroupDialog = false
            },
            content = {
                DodamMenuDialog(
                    items = persistentListOf(
                        DodamMenuItem(
                            item = "삭제하기",
                            color = DodamMenuItemColor.Negative,
                            onClickItem = {
                                isShowGroupDialog = false
                                viewModel.deleteDivision(divisionId = id)
                            },
                        ),
                    ),
                )
            },
        )
    }

    if (isShowMemberBottomSheet && bottomSheetUser != null) {
        DodamModalBottomSheet(
            onDismissRequest = {
                isShowMemberBottomSheet = false
                bottomSheetUser = null
            },
            space = 0.dp,
            shape = RoundedCornerShape(
                topStart = 28.dp,
                topEnd = 28.dp,
            ),
            title = {},
            content = {
                Text(
                    text = "${bottomSheetUser!!.memberName}님의 정보",
                    style = DodamTheme.typography.heading1Bold(),
                    color = DodamTheme.colors.labelNormal,
                )

                if (bottomSheetUser!!.grade != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${bottomSheetUser!!.grade}학년 ${bottomSheetUser!!.room}반 ${bottomSheetUser!!.number}번",
                        style = DodamTheme.typography.headlineMedium(),
                        color = DodamTheme.colors.labelAssistive,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "권한 정보",
                        style = DodamTheme.typography.heading1Bold(),
                        color = DodamTheme.colors.labelNormal,
                    )
                    if (isPermission) {
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberBounceIndication(),
                                    onClick = {
                                        dialogMemberUser = bottomSheetUser!!
                                        isShowMemberDialog = true
                                    },
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = DodamIcons.Menu.value,
                                contentDescription = "menu button",
                                tint = DodamTheme.colors.labelAssistive.copy(
                                    alpha = 0.5f,
                                ),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = when (bottomSheetUser!!.permission) {
                        DivisionPermission.ADMIN -> "관리자"
                        DivisionPermission.WRITER -> "부관리자"
                        DivisionPermission.READER -> "멤버"
                    },
                    style = DodamTheme.typography.headlineMedium(),
                    color = DodamTheme.colors.labelAssistive,
                )

                Spacer(modifier = Modifier.height(24.dp))
                if (isPermission) {
                    DodamButton(
                        text = "내보내기",
                        onClick = {
                            viewModel.kickMember(
                                divisionId = id,
                                memberId = bottomSheetUser!!.id,
                            )
                            isShowMemberBottomSheet = false
                            bottomSheetUser = null
                        },
                        buttonRole = ButtonRole.Assistive,
                        buttonSize = ButtonSize.Medium,
                    )
                }
            },
        )
    }

    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "",
                onBackClick = popBackStack,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 8.dp,
                            )
                            .fillMaxWidth()
                            .background(
                                color = DodamTheme.colors.backgroundNormal,
                                shape = DodamTheme.shapes.medium,
                            )
                            .padding(horizontal = 16.dp),
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = uiState.divisionInfo?.divisionName ?: "",
                                style = DodamTheme.typography.heading1Bold(),
                                color = DodamTheme.colors.labelNormal,
                            )
                            if (isPermission) {
                                Spacer(modifier = Modifier.weight(1f))
                                Box(
                                    modifier = Modifier.clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberBounceIndication(),
                                        onClick = {
                                            isShowGroupDialog = true
                                        },
                                    ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .size(18.dp),
                                        imageVector = DodamIcons.Menu.value,
                                        contentDescription = "메뉴 아이콘",
                                        tint = DodamTheme.colors.labelAssistive.copy(
                                            alpha = 0.5f,
                                        ),
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = uiState.divisionInfo?.description ?: "",
                            style = DodamTheme.typography.body1Medium(),
                            color = DodamTheme.colors.labelNeutral,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    if (isPermission) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    top = 12.dp,
                                    start = 16.dp,
                                    end = 16.dp,
                                )
                                .fillMaxWidth()
                                .height(60.dp)
                                .background(
                                    color = DodamTheme.colors.backgroundNormal,
                                    shape = DodamTheme.shapes.medium,
                                )
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 16.dp,
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberBounceIndication(),
                                        onClick = {
                                            navigateToGroupWaiting(id, uiState.divisionInfo!!.divisionName)
                                        },
                                    ),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "가입 신청",
                                    style = DodamTheme.typography.headlineBold(),
                                    color = DodamTheme.colors.labelStrong,
                                )
                                if (uiState.divisionPendingCnt > 0) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    DodamTag(
                                        text = "${uiState.divisionPendingCnt}",
                                        tagType = TagType.Primary,
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .width(1.dp)
                                    .fillMaxHeight()
                                    .background(
                                        color = DodamTheme.colors.lineAlternative,
                                    ),
                            )

                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberBounceIndication(),
                                        onClick = {
                                            navigateToGroupAdd(id)
                                        },
                                    ),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "멤버 추가",
                                    style = DodamTheme.typography.headlineBold(),
                                    color = DodamTheme.colors.labelStrong,
                                )
                            }
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 12.dp,
                            )
                            .weight(1f)
                            .fillMaxWidth()
                            .background(
                                color = DodamTheme.colors.backgroundNormal,
                                shape = DodamTheme.shapes.medium,
                            )
                            .padding(16.dp),
                    ) {
                        item {
                            Text(
                                text = "멤버",
                                style = DodamTheme.typography.headlineBold(),
                                color = DodamTheme.colors.labelNormal,
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "관리자",
                                style = DodamTheme.typography.body2Medium(),
                                color = DodamTheme.colors.labelAlternative,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        items(uiState.divisionAdminMembers, key = { it.id }) {
                            GroupDetailMemberCard(
                                image = it.profileImage,
                                name = it.memberName,
                                role = it.role.getName(),
                                onClick = {
                                    onClickMember(it)
                                },
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            DodamDivider(
                                modifier = Modifier.padding(horizontal = 8.dp),
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "부관리자",
                                style = DodamTheme.typography.body2Medium(),
                                color = DodamTheme.colors.labelAlternative,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        items(uiState.divisionWriterMembers.size) { index ->
                            val item = uiState.divisionWriterMembers[index]
                            GroupDetailMemberCard(
                                image = item.profileImage,
                                name = item.memberName,
                                role = item.role.getName(),
                                onClick = {
                                    onClickMember(item)
                                },
                            )
                            if (index != uiState.divisionWriterMembers.lastIndex) {
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            DodamDivider(
                                modifier = Modifier.padding(horizontal = 8.dp),
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "멤버",
                                style = DodamTheme.typography.body2Medium(),
                                color = DodamTheme.colors.labelAlternative,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        items(uiState.divisionReaderMembers.size) { index ->
                            val item = uiState.divisionReaderMembers[index]
                            GroupDetailMemberCard(
                                image = item.profileImage,
                                name = item.memberName,
                                role = item.role.getName(),
                                onClick = {
                                    onClickMember(item)
                                },
                            )
                            if (index != uiState.divisionReaderMembers.lastIndex) {
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }

                if (!isJoin) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(144.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                brush = Brush.linearGradient(
                                    colorStops = arrayOf(
                                        0f to DodamTheme.colors.staticWhite.copy(alpha = 0f),
                                        0.22f to DodamTheme.colors.staticWhite.copy(alpha = 0.22f),
                                        1f to DodamTheme.colors.staticWhite,
                                    ),
                                ),
                            ),
                    ) {
                        DodamButton(
                            modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp,
                                )
                                .fillMaxWidth()
                                .height(48.dp)
                                .align(Alignment.BottomCenter),
                            text = "가입 신청",
                            onClick = {
                                viewModel.requestJoinDivision(
                                    id = id,
                                )
                            },
                            buttonSize = ButtonSize.Large,
                            buttonRole = ButtonRole.Primary,
                        )
                    }
                }
            }
            if (uiState.requestLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = DodamTheme.colors.staticBlack.copy(
                                alpha = 0.3f,
                            ),
                        ),
                )
            }
        }
    }
}

@Composable
private fun GroupDetailMemberCard(image: String?, name: String, role: String, onClick: () -> Unit) {
    DodamGroupMemberCard(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberBounceIndication(),
            onClick = onClick,
        ),
        image = image,
        name = name,
        action = {
            Text(
                text = role,
                style = DodamTheme.typography.body2Medium(),
                color = DodamTheme.colors.labelAlternative,
            )
        },
    )
}
