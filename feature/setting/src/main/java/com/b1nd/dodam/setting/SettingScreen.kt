package com.b1nd.dodam.setting

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.b1nd.dodam.dds.component.DodamDialog
import com.b1nd.dodam.dds.component.DodamSmallTopAppBar
import com.b1nd.dodam.dds.component.button.DodamLargeFilledButton
import com.b1nd.dodam.dds.component.button.DodamTextButton
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.ChevronRightIcon
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.ui.component.InputField
import com.b1nd.dodam.ui.effect.shimmerEffect

@ExperimentalMaterial3Api
@Composable
internal fun SettingScreen(viewModel: SettingViewModel = hiltViewModel(), popBackStack: () -> Unit, logout: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showDeactivationDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    if (showLogoutDialog) {
        DodamDialog(
            onDismissRequest = { showLogoutDialog = false },
            confirmButton = {
                DodamLargeFilledButton(
                    modifier = Modifier.weight(1f),
                    onClick = logout,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError,
                    ),
                    isLoading = uiState.isLoading,
                ) {
                    Text(text = "로그아웃")
                }
            },
            dismissButton = {
                DodamLargeFilledButton(
                    modifier = Modifier.weight(1f),
                    onClick = { showLogoutDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
                    enabled = !uiState.isLoading,
                ) {
                    Text(text = "취소")
                }
            },
            title = {
                Text(text = "정말 로그아웃 하시겠어요?")
            },
        )
    }

    if (showDialog) {
        DodamDialog(
            onDismissRequest = { showDialog = false },
            confirmText = {
                DodamTextButton(onClick = { showDialog = false }) {
                    Text(text = "확인")
                }
            },
            title = { Text(text = "아직 준비 중인 기능이에요!") },
            text = { Text(text = "정보를 수정하시려면 도담도담 웹사이트를 이용해주세요.") },
        )
    }

    if (showDeactivationDialog) {
        DodamDialog(
            onDismissRequest = { showDeactivationDialog = false },
            confirmButton = {
                DodamLargeFilledButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.deactivate()
                        logout()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError,
                    ),
                    isLoading = uiState.isLoading,
                ) {
                    Text(text = "회원탈퇴")
                }
            },
            dismissButton = {
                DodamLargeFilledButton(
                    modifier = Modifier.weight(1f),
                    onClick = { showDeactivationDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    ),
                    enabled = !uiState.isLoading,
                ) {
                    Text(text = "취소")
                }
            },
            title = {
                Text(text = "정말 회원탈퇴 하시겠어요?")
            },
        )
    }

    Scaffold(
        topBar = {
            DodamSmallTopAppBar(
                title = {
                    Text(text = "설정")
                },
                onNavigationIconClick = popBackStack,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(top = 16.dp, end = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            InputField(
                onClick = { showDialog = true },
                text = {
                    if (uiState.isLoading) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        shimmerEffect(),
                                        CircleShape,
                                    ),
                            )

                            Column {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp, 20.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(4.dp),
                                        ),
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Box(
                                    modifier = Modifier
                                        .size(100.dp, 14.dp)
                                        .background(
                                            shimmerEffect(),
                                            RoundedCornerShape(4.dp),
                                        ),
                                )
                            }
                        }
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(48.dp),
                                model = uiState.profile
                                    ?: com.b1nd.dodam.ui.R.drawable.ic_default_profile,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                            )

                            Column {
                                BodyLarge(
                                    text = uiState.name,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                                LabelLarge(
                                    text = "내 정보 수정하기",
                                    color = MaterialTheme.colorScheme.tertiary,
                                )
                            }
                        }
                    }
                },
                content = {
                    ChevronRightIcon(
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant),
            )

            InputField(
                onClick = {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://dodam.b1nd.com/detailed-information/service-policy"),
                        ),
                    )
                },
                text = {
                    BodyLarge(
                        text = "서비스 운영 정책",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium,
                    )
                },
                content = {
                    ChevronRightIcon(
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
            )

            InputField(
                onClick = {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://dodam.b1nd.com/detailed-information/personal-information"),
                        ),
                    )
                },
                text = {
                    BodyLarge(
                        text = "개인정보 처리방침",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium,
                    )
                },
                content = {
                    ChevronRightIcon(
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
            )

            InputField(
                onClick = {},
                text = {
                    BodyLarge(
                        text = "버전 정보",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium,
                    )
                },
                content = {
                    BodyLarge(
                        text = "3.0.0",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Normal,
                    )
                },
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant),
            )

            InputField(
                onClick = { showLogoutDialog = true },
                text = {
                    BodyLarge(
                        text = "로그아웃",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium,
                    )
                },
                content = {
                    ChevronRightIcon(
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
            )

            InputField(
                onClick = { showDeactivationDialog = true },
                text = {
                    BodyLarge(
                        text = "회원탈퇴",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Medium,
                    )
                },
                content = {
                    ChevronRightIcon(
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
            )
        }
    }
}
