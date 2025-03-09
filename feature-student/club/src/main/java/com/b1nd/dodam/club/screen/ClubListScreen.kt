package com.b1nd.dodam.club.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.b1nd.dodam.club.model.Club
import com.b1nd.dodam.club.model.ClubPendingUiState
import com.b1nd.dodam.club.model.ClubState
import com.b1nd.dodam.club.model.ClubUiState
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ActionIcon
import com.b1nd.dodam.designsystem.component.DodamDefaultTopAppBar
import com.b1nd.dodam.designsystem.component.DodamEmpty
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.designsystem.component.DodamSegmentedButton
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.effect.shimmerEffect
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay

@Composable
internal fun ClubListScreen(state: ClubUiState, selectDetailClub: (Long, Club) -> Unit, navigateToApply: () -> Unit) {
    var clubTypeIndex by remember { mutableIntStateOf(0) }
    val clubTypeList = listOf(
        "창체",
        "자율",
    )

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000L)
        isLoading = false
    }

    val clubTypeItem = List(2) { index ->
        DodamSegment(
            selected = clubTypeIndex == index,
            text = clubTypeList[index],
            onClick = { clubTypeIndex = index },
        )
    }.toImmutableList()

    val uriHandler = LocalUriHandler.current
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                DodamDefaultTopAppBar(
                    title = "동아리",
                    modifier = Modifier.statusBarsPadding(),
                    actionIcons = persistentListOf(
                        ActionIcon(
                            icon = DodamIcons.Plus,
                            onClick = { navigateToApply() },
                            enabled = true,
                        ),
                    ),
                )
            },
            containerColor = DodamTheme.colors.backgroundNeutral,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                ) {
                    DodamSegmentedButton(
                        segments = clubTypeItem,
                        modifier = Modifier.padding(top = 16.dp),
                    )
                    when (val data = state.clubPendingUiState) {
                        ClubPendingUiState.Error -> {
                            Spacer(modifier = Modifier.height(20.dp))
                            DodamEmpty(
                                onClick = {
                                    uriHandler.openUri("https://dodam.b1nd.com/club/create")
                                },
                                title = "아직 등록된 동아리가 없어요",
                                buttonText = "동아리 생성하기",
                            )
                        }

                        ClubPendingUiState.Loading -> {
                            Column(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                DodamLoadingClub()
                                DodamLoadingClub()
                            }
                        }

                        is ClubPendingUiState.Success -> {
                            val clubs =
                                if (clubTypeIndex == 0) data.clubPendingList.creativeClubs else data.clubPendingList.selfClubs
                            if (clubs.isEmpty()) {
                                if (isLoading) {
                                    Column(
                                        modifier = Modifier
                                            .padding(top = 20.dp)
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(12.dp),
                                    ) {
                                        DodamLoadingClub()
                                        DodamLoadingClub()
                                    }
                                } else {
                                    Spacer(modifier = Modifier.height(20.dp))
                                    DodamEmpty(
                                        onClick = {
                                            uriHandler.openUri("https://dodam.b1nd.com/clubs/create")
                                        },
                                        title = "아직 등록된 동아리가 없어요",
                                        buttonText = "동아리 생성하기",
                                    )
                                }
                            } else {
                                LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
                                    items(clubs.size) { index ->
                                        DodamClub(
                                            modifier = Modifier
                                                .padding(bottom = 12.dp)
                                                .clickable {
                                                    selectDetailClub(
                                                        clubs[index].id.toLong(),
                                                        Club(
                                                            id = clubs[index].id,
                                                            name = clubs[index].name,
                                                            shortDescription = clubs[index].shortDescription,
                                                            description = clubs[index].description,
                                                            subject = clubs[index].subject,
                                                            type = clubs[index].type,
                                                            image = clubs[index].image,
                                                            teacher = clubs[index].teacher,
                                                            state = clubs[index].state,
                                                        ),
                                                    )
                                                },
                                            club = clubs[index],
                                        )
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

// TODO : 컴포넌트로 뺄 예정입니다.
@Composable
private fun DodamClub(modifier: Modifier = Modifier, club: Club) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(86.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (club.image.isNullOrEmpty()) {
            Box(
                modifier = Modifier
                    .width(83.dp)
                    .height(86.dp)
                    .background(
                        brush = shimmerEffect(),
                        shape = RoundedCornerShape(8.dp),
                    ),
            )
        } else {
            AsyncImage(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(8.dp))
                    .width(83.dp)
                    .height(86.dp),
                model = club.image,
                contentDescription = "동아리",
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = club.subject,
                style = DodamTheme.typography.caption1Medium(),
                color = DodamTheme.colors.labelAlternative,
            )
            Row {
                Text(
                    text = club.name,
                    style = DodamTheme.typography.headlineBold(),
                    color = DodamTheme.colors.labelNormal,
                )
            }
            Text(
                text = club.description,
                style = DodamTheme.typography.body2Medium(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = DodamTheme.colors.labelNormal,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .background(
                    color = when (club.state) {
                        ClubState.ALLOWED -> DodamTheme.colors.primaryNormal
                        ClubState.PENDING -> DodamTheme.colors.lineNormal
                        ClubState.REJECTED -> DodamTheme.colors.statusNegative
                        ClubState.WAITING -> DodamTheme.colors.lineNormal
                        ClubState.DELETED -> DodamTheme.colors.lineNormal
                    },
                    shape = RoundedCornerShape(28.dp),
                )
                .padding(vertical = 4.dp, horizontal = 8.dp),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = when (club.state) {
                    ClubState.ALLOWED -> "승인됨"
                    ClubState.PENDING -> "대기중"
                    ClubState.REJECTED -> "거절됨"
                    ClubState.WAITING -> "웨이팅"
                    ClubState.DELETED -> "삭제됨"
                },
                style = DodamTheme.typography.caption2Bold(),
                color = DodamTheme.colors.staticWhite,
            )
        }
    }
}

@Composable
private fun DodamLoadingClub(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(86.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .width(83.dp)
                .height(86.dp)
                .background(
                    brush = shimmerEffect(),
                    shape = RoundedCornerShape(8.dp),
                ),
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(17.dp)
                    .background(
                        brush = shimmerEffect(),
                        shape = RoundedCornerShape(4.dp),
                    ),
            )
            Spacer(modifier = Modifier.height(1.dp))
            Row {
                Box(
                    modifier = Modifier
                        .width(46.dp)
                        .height(27.dp)
                        .background(
                            brush = shimmerEffect(),
                            shape = RoundedCornerShape(4.dp),
                        ),
                )
            }
            Spacer(modifier = Modifier.height(1.dp))
            Box(
                modifier = Modifier
                    .width(273.dp)
                    .height(21.dp)
                    .background(
                        brush = shimmerEffect(),
                        shape = RoundedCornerShape(4.dp),
                    ),
            )
        }
    }
}
