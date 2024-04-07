package com.b1nd.dodam.student.point

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.data.point.model.ScoreType
import com.b1nd.dodam.dds.component.DodamDialog
import com.b1nd.dodam.dds.component.DodamSmallTopAppBar
import com.b1nd.dodam.dds.component.button.DodamSegment
import com.b1nd.dodam.dds.component.button.DodamSegmentedButtonRow
import com.b1nd.dodam.dds.component.button.DodamTextButton
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.BodyMedium
import com.b1nd.dodam.dds.style.HeadlineLarge
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.dds.style.TitleMedium
import com.b1nd.dodam.ui.effect.shimmerEffect

@ExperimentalMaterial3Api
@Composable
internal fun PointScreen(viewModel: PointViewModel = hiltViewModel(), popBackStack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedIndex by remember { mutableIntStateOf(0) }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect {
            when (it) {
                is Event.ShowDialog -> {
                    showDialog = true
                }
            }
        }
    }

    if (showDialog) {
        DodamDialog(
            onDismissRequest = {
                showDialog = false
                popBackStack()
            },
            confirmText = {
                DodamTextButton(onClick = {
                    showDialog = false
                    popBackStack()
                }) {
                    Text(text = "확인")
                }
            },
            title = { Text(text = "상벌점을 불러오지 못했어요") },
        )
    }

    Scaffold(
        topBar = {
            DodamSmallTopAppBar(
                title = { Text(text = "내 상벌점") },
                onNavigationIconClick = popBackStack,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(end = 24.dp, start = 24.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            DodamSegmentedButtonRow(selectedIndex = selectedIndex) {
                DodamSegment(selected = selectedIndex == 0, onClick = { selectedIndex = 0 }) {
                    Text(text = "기숙사")
                }

                DodamSegment(selected = selectedIndex == 1, onClick = { selectedIndex = 1 }) {
                    Text(text = "학교")
                }
            }

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    BodyMedium(text = "벌점", color = MaterialTheme.colorScheme.tertiary)
                    HeadlineLarge(
                        text = if (selectedIndex == 0) "${uiState.dormitoryPoint.first}점" else "${uiState.schoolPoint.first}점",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    BodyMedium(text = "상점", color = MaterialTheme.colorScheme.tertiary)
                    HeadlineLarge(
                        text = if (selectedIndex == 0) "${uiState.dormitoryPoint.second}점" else "${uiState.schoolPoint.second}점",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant),
            )

            TitleMedium(text = "상벌점 발급 내역")

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (uiState.isLoading) {
                    items(5) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(2.dp),
                            ) {
                                Box(modifier = Modifier
                                    .width(100.dp)
                                    .height(20.dp)
                                    .background(shimmerEffect(), RoundedCornerShape(4.dp))
                                )
                                Box(modifier = Modifier
                                    .width(80.dp)
                                    .height(14.dp)
                                    .background(shimmerEffect(), RoundedCornerShape(4.dp))
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Box(modifier = Modifier
                                .width(40.dp)
                                .height(20.dp)
                                .background(shimmerEffect(), RoundedCornerShape(4.dp))
                            )
                        }
                    }
                } else {
                    items(
                        items = if (selectedIndex == 0) {
                            uiState.dormitoryPointReasons
                        } else {
                            uiState.schoolPointReasons
                        },
                        key = { it.id },
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(2.dp),
                            ) {
                                BodyLarge(
                                    text = it.reason.reason,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                                LabelLarge(
                                    text = "${it.teacher.name} · ${it.issueAt}",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            BodyLarge(
                                text = "${it.reason.score}점",
                                color = if (it.reason.scoreType == ScoreType.MINUS) {
                                    MaterialTheme.colorScheme.error
                                } else {
                                    MaterialTheme.colorScheme.primary
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
