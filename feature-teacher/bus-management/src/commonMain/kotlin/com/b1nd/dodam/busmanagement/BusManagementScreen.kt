package com.b1nd.dodam.busmanagement

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.b1nd.dodam.designsystem.component.DodamTag
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TagType
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun BusManagementScreen(
    popBackStack: () -> Unit,
    navigateToBusRegister: () -> Unit,
) {
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "귀가 버스 관리",
                type = TopAppBarType.Small,
                onBackClick = popBackStack,
                actionIcons = persistentListOf(
                    ActionIcon(
                        icon = DodamIcons.Plus,
                        onClick = navigateToBusRegister,
                    )
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 4.dp)
        ) {
            DodamTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                value = searchText,
                label = "버스 검색",
                onValueChange = {
                    searchText = it
                },
                onClickRemoveRequest = {
                    searchText = ""
                },
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(10) {
                    BusCard(
                        title = "버스 ${it}호",
                        isRace = it % 2 == 0,
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Composable
private fun BusCard(
    modifier: Modifier = Modifier,
    title: String,
    isRace: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(),
                onClick = onClick,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = DodamTheme.typography.body1Medium(),
            color = DodamTheme.colors.labelNormal,
        )
        DodamTag(
            modifier = Modifier.padding(vertical = 10.dp),
            text = if (isRace) "운행" else "미운행",
            tagType = if (isRace) TagType.Primary else TagType.Negative
        )
    }
}