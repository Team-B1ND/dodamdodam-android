package com.b1nd.dodam.member

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.b1nd.dodam.dds.animation.bounceClick
import com.b1nd.dodam.dds.component.DodamTopAppBar
import com.b1nd.dodam.dds.component.button.DodamIconButton
import com.b1nd.dodam.dds.foundation.DodamIcons
import com.b1nd.dodam.dds.style.BodyLarge
import com.b1nd.dodam.dds.style.GearIcon
import com.b1nd.dodam.dds.style.LabelLarge
import com.b1nd.dodam.dds.theme.DodamTheme
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.b1nd.dodam.ui.icons.BarChart
import com.b1nd.dodam.ui.icons.ColoredBus
import com.b1nd.dodam.ui.icons.ColoredMegaphone
import com.b1nd.dodam.ui.icons.ColoredMusicalNote
import com.b1nd.dodam.ui.icons.ColoredPencil
import com.b1nd.dodam.ui.icons.ColoredTent
import com.b1nd.dodam.ui.icons.DefaultProfile
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllScreen(
    viewModel: AllViewModel = koinViewModel(),
    navigateToSetting: () -> Unit,
    navigateToMyPoint: () -> Unit,
    navigateToAddBus: () -> Unit,
    navigateToAddNightStudy: () -> Unit,
    navigateToAddOutingStudy: () -> Unit,
    navigateToSchedule: () -> Unit,
    navigateToWakeUpSong: () -> Unit,
    navigateToAddWakeUpSong: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            DodamTopAppBar(
                title = { Text(text = "전체") },
                actions = {
                    DodamIconButton(
                        onClick = {
                            navigateToSetting()
                        },
                    ) {
                        GearIcon()
                    }
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            if (uiState.isSimmer) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .background(
                                shimmerEffect(),
                                RoundedCornerShape(12.dp),
                            ),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(horizontalAlignment = Alignment.Start) {
                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .width(150.dp)
                                .background(shimmerEffect(), RoundedCornerShape(100)),
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .height(20.dp)
                                .width(80.dp)
                                .background(shimmerEffect(), RoundedCornerShape(100)),
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    uiState.memberInfo?.let { myInfo ->
                        Box {
                            if (myInfo.profileImage != null) {
                                AsyncImage(
                                    model = myInfo.profileImage,
                                    contentDescription = "profile",
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(12.dp))
                                        .size(70.dp),
                                    contentScale = ContentScale.Crop,
                                )
                            } else {
                                Image(
                                    bitmap = DefaultProfile,
                                    contentDescription = "profile",
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(12.dp))
                                        .size(70.dp),
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        val classInfo = myInfo.student
                        Column(horizontalAlignment = Alignment.Start) {
                            BodyLarge(
                                text = "환영합니다, " + myInfo.name + "님",
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            LabelLarge(
                                text = "${classInfo?.grade ?: 0}학년 ${classInfo?.room ?: 0}반 ${classInfo?.number ?: 0}번",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            AllCardView(
                imageVector = BarChart,
                text = "내 상벌점 보기",
            ) {
                navigateToMyPoint()
            }
            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                AllCardView(
                    imageVector = ColoredBus,
                    text = "복귀 버스 신청하기",
                ) {
                    navigateToAddBus()
                }
                AllCardView(
                    imageVector = ColoredPencil,
                    text = "심야 자습 신청하기",
                ) {
                    navigateToAddNightStudy()
                }
                AllCardView(
                    imageVector = ColoredTent,
                    text = "외출/외박 신청하기",
                ) {
                    navigateToAddOutingStudy()
                }
                AllCardView(
                    imageVector = ColoredMegaphone,
                    text = "기상송 보기",
                ) {
                    navigateToWakeUpSong()
                }
                AllCardView(
                    imageVector = ColoredMusicalNote,
                    text = "기상송 신청하기",
                ) {
                    navigateToAddWakeUpSong()
                }
            }
        }
    }
}

@Composable
fun AllCardView(imageVector: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .bounceClick(onClick = onClick)
            .padding(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(MaterialTheme.colorScheme.secondary, MaterialTheme.shapes.extraSmall)
                .padding(6.dp),
        ) {
            Image(
                imageVector = imageVector,
                contentDescription = "image",
                modifier = Modifier.size(20.dp),
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        BodyLarge(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = DodamIcons.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(14.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AllCardViewPreview() {
    DodamTheme {
        AllCardView(
            imageVector = BarChart,
            onClick = {},
            text = "test",
        )
    }
}
