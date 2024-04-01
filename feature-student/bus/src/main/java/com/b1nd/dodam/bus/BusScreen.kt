package com.b1nd.dodam.bus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.bus.model.Bus
import com.b1nd.dodam.dds.component.DodamLargeTopAppBar
import com.b1nd.dodam.dds.component.DodamToast
import com.b1nd.dodam.dds.foundation.DodamColor
import com.b1nd.dodam.dds.foundation.DodamIcons
import com.b1nd.dodam.dds.style.CheckmarkCircleFilledIcon
import com.b1nd.dodam.dds.style.HeadlineSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusScreen(
    viewModel: BusViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedIndex: Int? by remember {
        mutableStateOf(null)
    }
    var dialogMessage by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = dialogMessage) {
        if (dialogMessage.isNotEmpty()) {
            snackbarHostState.showSnackbar(dialogMessage)
        }
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
                    DodamToast(text = it.visuals.message, trailingIcon = {
                        CheckmarkCircleFilledIcon(
                            modifier = Modifier
                                .size(20.dp)
                                .drawBehind {
                                    drawRoundRect(
                                        color = DodamColor.White,
                                        topLeft = Offset(12f, 12f),
                                        size = Size(30f, 30f),
                                    )
                                },
                        )
                    })
                    Spacer(modifier = Modifier.height(90.dp))
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn {
                items(uiState.buses.size) { index ->
                    val bus = uiState.buses[index]
                    BusCard(
                        bus = bus,
                        isSelected = selectedIndex == index,
                        onClick = {
                            if (bus.applyCount == bus.peopleLimit) {
                                dialogMessage = "버스가 만석이에요"
                            } else {
                                // TODO Apply Bus
                                selectedIndex = index
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun BusCard(bus: Bus, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = bus.busName,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 18.sp
            ),
        )
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = "${bus.applyCount}/${bus.peopleLimit}",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 18.sp),
            color = if (bus.applyCount >= bus.peopleLimit) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        if (isSelected) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = DodamIcons.Checkmark,
                contentDescription = "check",
                tint = MaterialTheme.colorScheme.primary,
            )
        } else {
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Preview
@Composable
fun BusScreenPreView() {
    BusScreen { }
}
