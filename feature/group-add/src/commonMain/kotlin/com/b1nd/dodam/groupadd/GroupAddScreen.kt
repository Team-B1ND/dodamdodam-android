package com.b1nd.dodam.groupadd

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.b1nd.dodam.data.division.model.DivisionMember
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.groupadd.model.GroupAddSideEffect
import com.b1nd.dodam.groupadd.viewmodel.GroupAddViewModel
import com.b1nd.dodam.ui.component.DodamGroupMemberCard
import com.b1nd.dodam.ui.component.SnackbarState
import com.b1nd.dodam.ui.icons.ColoredCheckmarkCircle
import com.b1nd.dodam.ui.icons.ColoredCheckmarkCircleFilled
import com.b1nd.dodam.ui.util.addFocusCleaner
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun GroupAddScreen(
    id: Int,
    viewModel: GroupAddViewModel = koinViewModel(),
    showSnackbar: (state: SnackbarState, message: String) -> Unit,
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val lazyListState = rememberLazyListState()
    val focusManager = LocalFocusManager.current
    var checkUsers: ImmutableList<String> by remember { mutableStateOf(persistentListOf()) }

    LaunchedEffect(true) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                GroupAddSideEffect.FailedAddMember -> {
                    showSnackbar(SnackbarState.ERROR, "멤버 추가에 실패했어요")
                }
                GroupAddSideEffect.SuccessAddMember -> {
                    checkUsers = persistentListOf()
                    showSnackbar(SnackbarState.SUCCESS, "멤버 추가에 성공했어요")
                    popBackStack()
                }
            }
        }
    }

    LaunchedEffect(lazyListState.canScrollForward) {
        if (!lazyListState.canScrollForward) {
            viewModel.loadNextGroupList()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            modifier = Modifier.addFocusCleaner(focusManager),
            containerColor = DodamTheme.colors.backgroundNeutral,
            topBar = {
                DodamTopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    title = "멤버 추가",
                    onBackClick = popBackStack,
                )
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = lazyListState,
                ) {
                    items(uiState.divisions.size) {
                        val item = uiState.divisions[it]
                        GroupCard(
                            title = item.name,
                            users = uiState.divisionMembers.getOrElse(
                                item.id,
                                defaultValue = { persistentListOf() },
                            ),
                            checkUsers = checkUsers,
                            isOpen = item.isOpen,
                            onClickCard = {
                                if (uiState.divisionMembers[item.id] == null) {
                                    viewModel.loadDivisionMembers(
                                        divisionId = item.id,
                                    )
                                }
                                viewModel.changeOpenState(divisionId = item.id)
                            },
                            onClickUserAll = {
                                val userIds =
                                    uiState.divisionMembers[item.id]?.map { it.memberId } ?: emptyList()
                                val updatedSelection = checkUsers.toMutableList()
                                if (checkUsers.containsAll(userIds)) {
                                    updatedSelection.removeAll(userIds)
                                } else {
                                    updatedSelection.addAll(
                                        userIds.filterNot {
                                            checkUsers.contains(
                                                it,
                                            )
                                        },
                                    )
                                }
                                checkUsers = updatedSelection.toImmutableList()
                            },
                            onClickUser = { user ->
                                val updatedSelection = checkUsers.toMutableList()
                                if (checkUsers.contains(user.memberId)) {
                                    updatedSelection.remove(user.memberId)
                                } else {
                                    updatedSelection.add(user.memberId)
                                }
                                checkUsers = updatedSelection.toImmutableList()
                            },
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(
                            color = DodamTheme.colors.backgroundNeutral,
                        )
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 12.dp,
                        ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    DodamButton(
                        modifier = Modifier.weight(2f),
                        text = "전체 취소",
                        onClick = {
                            checkUsers = persistentListOf()
                        },
                        buttonSize = ButtonSize.Large,
                        buttonRole = ButtonRole.Assistive,
                    )
                    DodamButton(
                        modifier = Modifier.weight(3f),
                        text = "추가",
                        onClick = {
                            viewModel.addDivisionMember(
                                divisionId = id,
                                memberIds = checkUsers,
                            )
                        },
                        enabled = checkUsers.isNotEmpty(),
                        buttonSize = ButtonSize.Large,
                        buttonRole = ButtonRole.Primary,
                    )
                }
            }
        }

        if (uiState.addLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DodamTheme.colors.staticBlack.copy(alpha = 0.3f)),
            )
        }
    }
}

@Composable
private fun GroupCard(
    title: String,
    isOpen: Boolean,
    users: ImmutableList<DivisionMember>,
    checkUsers: ImmutableList<String>,
    onClickCard: () -> Unit,
    onClickUserAll: () -> Unit,
    onClickUser: (DivisionMember) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(
                    radius = DodamTheme.shapes.medium,
                ),
                onClick = onClickCard,
            )
            .background(
                color = DodamTheme.colors.backgroundNormal,
                shape = DodamTheme.shapes.medium,
            )
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = DodamTheme.typography.headlineBold(),
                color = DodamTheme.colors.labelNormal,
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier.size(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier
                        .size(18.dp)
                        .rotate(if (isOpen) 90f else -90f),
                    imageVector = DodamIcons.ChevronRight.value,
                    contentDescription = null,
                    tint = DodamTheme.colors.labelAssistive,
                )
            }
        }
        if (isOpen) {
            Spacer(modifier = Modifier.height(16.dp))
            if (users.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberBounceIndication(),
                            onClick = onClickUserAll,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier
                            .padding(
                                start = 4.dp,
                                end = 8.dp,
                                top = 4.dp,
                                bottom = 4.dp,
                            )
                            .size(24.dp),
                        imageVector = if (checkUsers.containsAll(users.map { it.memberId })) ColoredCheckmarkCircleFilled else ColoredCheckmarkCircle,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(DodamTheme.colors.primaryNormal),
                    )
                    Text(
                        text = "전체",
                        style = DodamTheme.typography.body1Bold(),
                        color = DodamTheme.colors.labelNormal,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            users.fastForEachIndexed { index, user ->
                DodamGroupMemberCard(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberBounceIndication(),
                        onClick = {
                            onClickUser(user)
                        },
                    ),
                    image = null,
                    name = user.memberName,
                    action = {
                        if (user.memberId in checkUsers) {
                            Image(
                                modifier = Modifier
                                    .size(24.dp),
                                imageVector = ColoredCheckmarkCircleFilled,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(DodamTheme.colors.primaryNormal),
                            )
                        }
                    },
                )
                if (index != users.lastIndex) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}
