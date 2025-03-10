package com.b1nd.dodam.group

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ActionIcon
import com.b1nd.dodam.designsystem.component.DodamTab
import com.b1nd.dodam.designsystem.component.DodamTabRow
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.group.model.GroupSideEffect
import com.b1nd.dodam.group.viewmodel.GroupViewModel
import kotlinx.collections.immutable.persistentListOf
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun GroupScreen(
    viewModel: GroupViewModel = koinViewModel(),
    isTeacher: Boolean,
    popBackStack: () -> Unit,
    navigateToGroupCreate: () -> Unit,
    navigateToGroupDetail: (id: Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    val myGroupLazyListState = rememberLazyListState()
    val allGroupLazyListState = rememberLazyListState()
    var selectedIndex by remember { mutableIntStateOf(0) }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        viewModel.sideEffect.collect {
            when (it) {
                GroupSideEffect.FailedLoad -> {
                    // TODO 예외 처리할 것.
                }
            }
        }
    }

    LaunchedEffect(selectedIndex, myGroupLazyListState.canScrollForward) {
        if (selectedIndex == 0 && !myGroupLazyListState.canScrollForward) {
            viewModel.loadMyGroupNext()
        }
    }

    LaunchedEffect(selectedIndex, allGroupLazyListState.canScrollForward) {
        if (selectedIndex == 1 && !allGroupLazyListState.canScrollForward) {
            viewModel.loadAllGroupNext()
        }
    }

    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "그룹",
                onBackClick = popBackStack,
                actionIcons = if (isTeacher) {
                    persistentListOf(
                        ActionIcon(
                            icon = DodamIcons.Plus,
                            onClick = navigateToGroupCreate,
                        ),
                    )
                } else {
                    persistentListOf()
                },
            )
        },
        containerColor = DodamTheme.colors.backgroundNormal,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(top = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DodamTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                value = searchText,
                label = "그룹 검색",
                onValueChange = {
                    searchText = it
                },
                onClickRemoveRequest = {
                    searchText = ""
                },
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(16.dp))
            DodamTabRow(
                modifier = Modifier.fillMaxWidth(),
                tabs = persistentListOf(
                    DodamTab(
                        selected = selectedIndex == 0,
                        label = "내 그룹",
                        onClick = {
                            selectedIndex = 0
                        },
                    ),
                    DodamTab(
                        selected = selectedIndex == 1,
                        label = "전체",
                        onClick = {
                            selectedIndex = 1
                        },
                    ),
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = if (selectedIndex == 0) myGroupLazyListState else allGroupLazyListState,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = (if (selectedIndex == 0) uiState.myGroups else uiState.allGroups)
                        .filter { filterItem ->
                            if (searchText.isEmpty()) {
                                return@filter true
                            }
                            filterItem.name.lowercase().contains(searchText.lowercase())
                        },
                    key = { item ->
                        item.id
                    },
                ) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(horizontal = 16.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberBounceIndication(),
                                onClick = { navigateToGroupDetail(item.id) },
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = item.name,
                            style = DodamTheme.typography.body1Medium(),
                            color = DodamTheme.colors.labelNormal,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = DodamIcons.ChevronRight.value,
                            contentDescription = null,
                            tint = DodamTheme.colors.labelAssistive,
                        )
                    }
                }
            }
        }
    }
}
