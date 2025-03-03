package com.b1nd.dodam.club

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamEmpty
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import org.koin.androidx.compose.koinViewModel

@Composable
fun ClubScreen(
    modifier: Modifier = Modifier,
    viewModel: ClubViewModel = koinViewModel(),
    popBackStack: () -> Unit,
    showToast: (String, String) -> Unit
) {
    val num by remember {
        mutableIntStateOf(2)
    }

    val lis = remember {
        mutableListOf("B1ND", "CNS", "DUCAMI")
    }

    val lis2 = remember {
        mutableListOf("자탄학", "inD", "Draw")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(DodamTheme.colors.backgroundNeutral),
            topBar = {
                DodamTopAppBar(
                    modifier = Modifier
                        .statusBarsPadding()
                        .background(DodamTheme.colors.backgroundNeutral),
                    title = "MY",
                    onBackClick = popBackStack,
                )
            },
            containerColor = DodamTheme.colors.backgroundNeutral,
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .background(DodamTheme.colors.backgroundNeutral, RoundedCornerShape(16.dp)),
            ) {
                when (num) {

                    0 -> {
                        DodamEmpty(
                            title = "아직 동아리에 신청하지 않았어요! \n" +
                                    "신청 마감 : 2025. 03. 19.",
                            buttonText = "동아리 입부 신청하기",
                            onClick = {},
                        )
                    }

                    1 -> {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "내 신청",
                                fontSize = 18.sp,
                                fontWeight = FontWeight(700)
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = "창체",
                                fontSize = 12.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            for (item in lis) {
                                var n = 1
                                Row(
                                    modifier = modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "${n}지망",
                                        fontSize = 15.sp
                                    )
                                    Text(
                                        text = item,
                                        fontSize = 15.sp
                                    )
                                }
                                Spacer(Modifier.height(8.dp))
                                n += 1
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "자율",
                                fontSize = 12.sp
                            )
                            for (item in lis2) {
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = item,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }

                    2 -> {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "소속된 동아리",
                                    fontSize = 18.sp
                                )
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    text = "창체",
                                    fontSize = 12.sp
                                )
                                Spacer(Modifier.height(8.dp))
                                Row(
                                    modifier = modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "B1ND",
                                        fontSize = 15.sp
                                    )
                                    Box(
                                        modifier = modifier
                                            .background(
                                                color = Color(0x330083F0),
                                                shape = RoundedCornerShape(size = 8.dp)
                                            )
                                            .padding(vertical = 4.dp, horizontal = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "입부 완료",
                                            color = Color(0xFF0083F0),
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }

                            Spacer(Modifier.height(12.dp))

                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "내 개설 신청",
                                    fontSize = 18.sp
                                )
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    text = "자율",
                                    fontSize = 12.sp
                                )
                                Spacer(Modifier.height(4.dp))
                                for (item in lis2) {
                                    Spacer(Modifier.height(4.dp))
                                    Row(
                                        modifier = modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = item,
                                            fontSize = 15.sp
                                        )
                                        DodamIcons.CheckmarkCircleFilled
                                    }
                                }
                            }

                            Spacer(Modifier.height(12.dp))

                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "받은 자율 부원 제안",
                                    fontSize = 18.sp
                                )
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    text = "자율",
                                    fontSize = 12.sp
                                )
                                Spacer(Modifier.height(4.dp))
                                for (item in lis2) {
                                    Spacer(Modifier.height(4.dp))
                                    Row(
                                        modifier = modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = item,
                                            fontSize = 15.sp
                                        )
                                        Row {
                                            DodamIcons.CheckmarkCircleFilled
                                            Spacer(Modifier.width(16.dp))
                                            DodamIcons.CheckmarkCircle
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClubJoinAlert() {

}

