package com.b1nd.dodam.bus

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.bus.model.Bus
import com.b1nd.dodam.dds.component.DodamDialog
import com.b1nd.dodam.dds.component.DodamLargeTopAppBar
import com.b1nd.dodam.dds.component.DodamToast
import com.b1nd.dodam.dds.component.button.DodamCTAButton
import com.b1nd.dodam.dds.component.button.DodamTextButton
import com.b1nd.dodam.dds.foundation.DodamColor
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.CheckmarkCircleFilledIcon
import com.b1nd.dodam.dds.style.CheckmarkIcon
import com.b1nd.dodam.dds.style.HeadlineSmall
import com.b1nd.dodam.dds.style.TitleLarge
import com.b1nd.dodam.dds.style.XMarkCircleIcon
import com.b1nd.dodam.ui.component.InputField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusScreen(viewModel: BusViewModel = hiltViewModel(), popBackStack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
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
                snackbarHostState.showSnackbar(toastMessage)
            } else {
                snackbarHostState.showSnackbar(toastMessage)
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
        DodamDialog(
            onDismissRequest = { showDialog = false },
            confirmText = {
                DodamTextButton(onClick = popBackStack) {
                    BodyLarge(text = "확인")
                }
            },
            title = {
                TitleLarge(text = "버스 운헹 날짜가 아닙니다")
            },
        )
    }

    Scaffold(
        topBar = {
            DodamLargeTopAppBar(
                title = {
                    HeadlineSmall(text = "무슨 버스에\n탑승하실건가요?")
                },
                onNavigationIconClick = popBackStack,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Column {
                    DodamToast(
                        text = it.visuals.message,
                        trailingIcon = {
                            if (!uiState.isError) {
                                CheckmarkCircleFilledIcon(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .drawBehind {
                                            drawRoundRect(
                                                color = DodamColor.White,
                                                topLeft = Offset(20f, 20f),
                                                size = Size(45f, 45f),
                                            )
                                        },
                                )
                            } else {
                                XMarkCircleIcon(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .drawBehind {
                                            drawRoundRect(
                                                color = DodamColor.White,
                                                topLeft = Offset(20f, 20f),
                                                size = Size(45f, 45f),
                                            )
                                        },
                                    tint = MaterialTheme.colorScheme.error,
                                )
                            }
                        },
                        iconColor = if (uiState.isError) MaterialTheme.colorScheme.error else DodamColor.Green,
                    )
                    Spacer(modifier = Modifier.height(90.dp))
                }
            }
        },
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
            DodamCTAButton(
                onClick = {
                    if (uiState.selectedBus == null) {
                        if (selectedIndex != null) {
                            viewModel.applyBus(uiState.buses[selectedIndex!!].id)
                        }
                    } else {
                        if (selectedIndex != null) {
                            viewModel.updateBus(
                                uiState.buses[selectedIndex!!].id,
                                selectedIndex!!,
                            )
                        } else {
                            viewModel.deleteBus(uiState.selectedBus!!.id)
                        }
                    }
                },
                enabled = !(uiState.selectedBus == null && selectedIndex == null),
            ) {
                BodyLarge(text = "확인")
            }
        }
    }
}

@Composable
fun BusCard(bus: Bus, isSelected: Boolean, onClick: () -> Unit) {
    InputField(
        onClick = onClick,
        text = {
            Text(
                text = bus.busName,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 18.sp,
                ),
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        content = {
            Text(
                text = "${bus.applyCount}/${bus.peopleLimit}",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 18.sp),
                color = if (bus.applyCount >= bus.peopleLimit) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.tertiary
                },
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (isSelected) {
                CheckmarkIcon(
                    modifier = Modifier.size(20.dp),
                    contentDescription = "check",
                    tint = MaterialTheme.colorScheme.primary,
                )
            } else {
                Spacer(modifier = Modifier.width(20.dp))
            }
        },
    )
}
