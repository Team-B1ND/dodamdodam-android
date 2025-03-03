package com.b1nd.dodam.bus

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.b1nd.dodam.club.model.Bus
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.component.TopAppBarType
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.component.InputField
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusScreen(viewModel: BusViewModel = koinViewModel(), popBackStack: () -> Unit, showToast: (String, String) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedIndex: Int? by remember {
        mutableStateOf(null)
    }
    var toastMessage by remember {
        mutableStateOf("")
    }

    var showDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = uiState.selectedBus) {
        if (uiState.selectedBus != null) {
            uiState.buses.forEachIndexed { index, bus ->
                if (bus.id == uiState.selectedBus?.id) {
                    selectedIndex = index
                }
            }
        }
    }
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
    LaunchedEffect(key1 = viewModel.event) {
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

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
                popBackStack()
            },
        ) {
            DodamDialog(
                confirmButton = popBackStack,
                title = "버스 운행 날짜가 아니에요",
            )
        }
    }

    Scaffold(
        topBar = {
            DodamTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "무슨 버스에\n탑승하실 건가요?",
                onBackClick = popBackStack,
                type = TopAppBarType.Medium,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(uiState.buses.size) { index ->
                    val bus = uiState.buses[index]
                    BusCard(
                        bus = bus,
                        isSelected = selectedIndex == index,
                        onClick = {
                            selectedIndex = if (selectedIndex == index) null else index
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1.0f))
            DodamButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                onClick = {
                    if (uiState.selectedBus == null) {
                        if (selectedIndex != null) {
                            viewModel.applyBus(uiState.buses[selectedIndex!!].id)
                        }
                    } else {
                        if (selectedIndex != null && uiState.selectedBus?.id != uiState.buses.getOrNull(selectedIndex ?: 0)?.id) {
                            viewModel.updateBus(
                                uiState.buses[selectedIndex!!].id,
                                selectedIndex!!,
                            )
                        } else {
                            viewModel.deleteBus(uiState.selectedBus!!.id)
                        }
                    }
                },
                text = if (uiState.selectedBus?.id == uiState.buses.getOrNull(selectedIndex ?: 0)?.id) "취소" else "신청",
                enabled = !(uiState.selectedBus == null && selectedIndex == null) && !uiState.isLoading,
                loading = uiState.isLoading,
            )
        }
    }
}

@Composable
fun BusCard(bus: Bus, isSelected: Boolean, onClick: () -> Unit) {
    InputField(
        onClick = onClick,
        enabled = !isSelected,
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
                color = when {
                    bus.applyCount >= bus.peopleLimit -> DodamTheme.colors.statusNegative
                    isSelected -> DodamTheme.colors.primaryNormal
                    else -> DodamTheme.colors.labelAlternative
                },
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (isSelected) {
                Image(
                    imageVector = DodamIcons.Checkmark.value,
                    modifier = Modifier.size(20.dp),
                    contentDescription = "check",
                    colorFilter = ColorFilter.tint(DodamTheme.colors.primaryNormal),
                )
            } else {
                Spacer(modifier = Modifier.width(20.dp))
            }
        },
    )
}
