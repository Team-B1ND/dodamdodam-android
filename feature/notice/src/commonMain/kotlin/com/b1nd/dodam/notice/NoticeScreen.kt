package com.b1nd.dodam.notice

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.util.fastForEach
import coil3.compose.AsyncImage
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ActionIcon
import com.b1nd.dodam.designsystem.component.DividerType
import com.b1nd.dodam.designsystem.component.DodamDefaultTopAppBar
import com.b1nd.dodam.designsystem.component.DodamDivider
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.component.DodamAutoLinkText
import com.b1nd.dodam.ui.component.modifier.`if`
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun NoticeScreen() {
    val uriHandler = LocalUriHandler.current
    var selectCategory by remember { mutableStateOf("전체") }

    Scaffold(
        topBar = {
            DodamDefaultTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = "공지",
                actionIcons = persistentListOf(
                    ActionIcon(
                        icon = DodamIcons.MagnifyingGlass,
                        onClick = {},
                        enabled = false,
                    ),
                ),
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            stickyHeader {
                Column {
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .background(DodamTheme.colors.backgroundNeutral)
                            .padding(start = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        persistentListOf("전체", "B1ND", "CNS", "DUCAMI", "삼디", "모디").fastForEach { category ->
                            NoticeCategoryCard(
                                text = category,
                                isChecked = category == selectCategory,
                                onClick = {
                                    selectCategory = category
                                },
                            )
                        }
                    }
                    DodamDivider(
                        type = DividerType.Normal,
                    )
                }
            }

            items(3) { index ->
                when (index) {
                    0 -> {
                        NoticeCard(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            author = "테스트",
                            date = "5월 4일 수요일",
                            title = "취업합시다",
                            content = "얼른",
                            images = persistentListOf(),
                            onLinkClick = { url ->
                                uriHandler.openUri(url)
                            },
                        )
                    }
                    1 -> {
                        NoticeCard(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            author = "정진",
                            date = "5월 4일 수요일",
                            title = "[겨울방학 전공역량 강화과정 1차 수요조사]",
                            content = "-본 수요조사 결과를 토대로 신청이 적은 강의는 폐강되며, 2차 조사가 실시됩니다.\n" +
                                "-프로젝트 코스 신청은 추후에 설문 예정입니다.\n" +
                                "-설문대상: 강의코스, 반반코스 희망 학생\n" +
                                "-설문기간: 10/30(수) 13시까지\n" +
                                "\n" +
                                "https://forms.gle/wfp1fRNNfcr11RcHA",
                            images = persistentListOf(),
                            onLinkClick = { url ->
                                uriHandler.openUri(url)
                            },
                        )
                    }
                    2 -> {
                        NoticeCard(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            author = "문주호",
                            date = "10월 28일 월요일",
                            title = "엑스코 앞에 동대구역 직행 버스가 있어요",
                            content = "타지역분들 참고해주세요",
                            images = persistentListOf(
                                "https://cdn.cvinfo.com/news/photo/202301/24433_27847_5741.jpg",
                                "https://cdn.cvinfo.com/news/photo/202301/24433_27847_5741.jpg",
                                "https://cdn.cvinfo.com/news/photo/202301/24433_27847_5741.jpg",
                            ),
                            onLinkClick = { url ->
                                uriHandler.openUri(url)
                            },
                        )
                    }
                }
            }
            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

@Composable
private fun NoticeCategoryCard(modifier: Modifier = Modifier, text: String, isChecked: Boolean, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(
                    radius = RoundedCornerShape(31.dp),
                ),
                onClick = onClick,
            )
            .background(
                color = if (isChecked) DodamTheme.colors.primaryNormal else DodamTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(31.dp),
            )
            .`if`(!isChecked) {
                border(
                    width = 1.dp,
                    color = DodamTheme.colors.lineAlternative,
                    shape = RoundedCornerShape(31.dp),
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = 18.dp,
                vertical = 8.dp,
            ),
            text = text,
            style = DodamTheme.typography.labelMedium(),
            color = if (isChecked) DodamTheme.colors.staticWhite else DodamTheme.colors.labelAlternative,
        )
    }
}

@Composable
private fun NoticeCard(
    modifier: Modifier = Modifier,
    author: String,
    date: String,
    title: String,
    content: String,
    images: ImmutableList<String>,
    onLinkClick: (url: String) -> Unit,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = DodamTheme.colors.backgroundNormal,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "$author · $date",
                style = DodamTheme.typography.labelRegular().copy(
                    lineHeight = 1.3.em,
                ),
                color = DodamTheme.colors.labelAssistive,
            )

            Text(
                text = title,
                style = DodamTheme.typography.heading2Bold(),
                color = DodamTheme.colors.labelNormal,
            )

            DodamAutoLinkText(
                text = content,
                style = DodamTheme.typography.labelRegular().copy(
                    lineHeight = 1.3.em,
                ),
                color = DodamTheme.colors.labelNormal,
                onLinkClick = onLinkClick,
            )

            if (images.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .weight(1f)
                            .height(172.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        model = images.first(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                    if (images.size > 1) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(172.dp)
                                .clip(RoundedCornerShape(12.dp)),
                        ) {
                            AsyncImage(
                                modifier = Modifier.fillMaxSize(),
                                model = images[1],
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                            )
                            if (images.size > 2) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = DodamTheme.colors.staticBlack.copy(
                                                alpha = 0.5f,
                                            ),
                                        ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = "+${images.size - 2}",
                                        style = DodamTheme.typography.title1Regular(),
                                        color = DodamTheme.colors.staticWhite,
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
