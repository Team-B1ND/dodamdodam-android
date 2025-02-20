package com.b1nd.dodam.notice

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import coil3.compose.AsyncImage
import com.b1nd.dodam.common.utiles.buildPersistentList
import com.b1nd.dodam.common.utiles.formatLocalDateTime
import com.b1nd.dodam.data.division.model.DivisionOverview
import com.b1nd.dodam.data.notice.model.NoticeFileType
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.component.ActionIcon
import com.b1nd.dodam.designsystem.component.DividerType
import com.b1nd.dodam.designsystem.component.DodamDefaultTopAppBar
import com.b1nd.dodam.designsystem.component.DodamDivider
import com.b1nd.dodam.designsystem.component.DodamTextButton
import com.b1nd.dodam.designsystem.component.TextButtonSize
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.notice.viewmodel.NoticeViewModel
import com.b1nd.dodam.ui.component.DodamAutoLinkText
import com.b1nd.dodam.ui.component.modifier.`if`
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalFoundationApi::class, KoinExperimentalAPI::class)
@Composable
internal fun NoticeScreen(
    viewModel: NoticeViewModel = koinViewModel(),
    isTeacher: Boolean,
    changeBottomNavVisible: (visible: Boolean) -> Unit,
    navigateToNoticeCreate: (() -> Unit)?,
) {
    val uiState by viewModel.uiState.collectAsState()
    val uriHandler = LocalUriHandler.current
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()

    var selectCategory by remember { mutableStateOf(DivisionOverview(id = 0, name = "전체")) }
    var isSearchMode by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val searchLazyListState = rememberLazyListState()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(true) {
        viewModel.loadDivision()
    }

    LaunchedEffect(lazyListState.canScrollForward, selectCategory) {
        if (isSearchMode || lazyListState.canScrollForward) {
            return@LaunchedEffect
        }
        viewModel.loadNextNoticeWithCategory(
            categoryId = selectCategory.id,
        )
        lazyListState.scrollToItem(0)
    }

    LaunchedEffect(searchLazyListState.canScrollForward, isSearchMode) {
        if (!isSearchMode && searchLazyListState.canScrollForward) {
            return@LaunchedEffect
        }
        viewModel.loadNextNoticeWithKeyword(
            keyword = searchText,
        )
    }

    LaunchedEffect(searchText) {
        if (isSearchMode) {
            delay(200)
            viewModel.loadNextNoticeWithKeyword(
                keyword = searchText,
            )
        }
    }

    LaunchedEffect(isSearchMode) {
        changeBottomNavVisible(!isSearchMode)
    }

    DisposableEffect(true) {
        onDispose {
            changeBottomNavVisible(true)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!isSearchMode) {
                DodamDefaultTopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    title = "공지",
                    actionIcons = buildPersistentList {
                        if (isTeacher) {
                            add(
                                ActionIcon(
                                    icon = DodamIcons.Plus,
                                    onClick = {
                                        if (navigateToNoticeCreate == null) {
                                            return@ActionIcon
                                        }
                                        navigateToNoticeCreate()
                                    },
                                    enabled = true,
                                ),
                            )
                        }
                        add(
                            ActionIcon(
                                icon = DodamIcons.MagnifyingGlass,
                                onClick = {
                                    isSearchMode = true
                                    coroutineScope.launch {
                                        delay(100)
                                        focusRequester.requestFocus()
                                    }
                                },
                                enabled = true,
                            ),
                        )
                    },
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier
                            .statusBarsPadding()
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(8.dp),
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .background(
                                    color = DodamTheme.colors.fillNeutral,
                                    shape = DodamTheme.shapes.extraSmall,
                                )
                                .padding(4.dp),
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(20.dp),
                                imageVector = DodamIcons.MagnifyingGlass.value,
                                contentDescription = "검색 아이콘",
                                tint = DodamTheme.colors.labelAlternative,
                            )
                            BasicTextField(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(36.dp)
                                    .focusRequester(focusRequester),
                                value = searchText,
                                onValueChange = {
                                    searchText = it
                                },
                                textStyle = DodamTheme.typography.body1Bold().copy(
                                    color = DodamTheme.colors.labelNormal,
                                ),
                                maxLines = 1,
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        contentAlignment = Alignment.CenterStart,
                                    ) {
                                        if (searchText == "") {
                                            Text(
                                                text = "검색",
                                                style = DodamTheme.typography.body1Bold(),
                                                color = DodamTheme.colors.labelAssistive,
                                            )
                                        }
                                        innerTextField()
                                    }
                                },
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        DodamTextButton(
                            onClick = {
                                isSearchMode = false
                            },
                            text = "닫기",
                            size = TextButtonSize.Large,
                        )
                    }
                    DodamDivider(
                        type = DividerType.Normal,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = if (isSearchMode) searchLazyListState else lazyListState,
        ) {
            if (!isSearchMode) {
                stickyHeader {
                    Column {
                        LazyRow(
                            modifier = Modifier
                                .background(DodamTheme.colors.backgroundNeutral)
                                .padding(
                                    start = 16.dp,
                                    top = 20.dp,
                                    bottom = 20.dp,
                                ),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(uiState.divisionList.size) { index ->
                                val item = uiState.divisionList[index]
                                NoticeCategoryCard(
                                    text = item.name,
                                    isChecked = item == selectCategory,
                                    onClick = {
                                        selectCategory = item
                                        viewModel.loadNextNoticeWithCategory(
                                            categoryId = item.id,
                                        )
                                    },
                                )
                            }
                        }
                        DodamDivider(
                            type = DividerType.Normal,
                        )
                    }
                }

                items(uiState.noticeList.size) { index ->
                    val item = uiState.noticeList[index]
                    NoticeCard(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        author = item.memberInfoRes.name,
                        date = formatLocalDateTime(item.createdAt),
                        title = item.title,
                        content = item.content,
                        images = item.noticeFileRes
                            .filter { it.fileType == NoticeFileType.IMAGE }
                            .map {
                                it.fileUrl
                            }.toImmutableList(),
                        onLinkClick = { url ->
                            uriHandler.openUri(url)
                        },
                    )
                }
            } else {
                items(uiState.searchNoticeList.size) { index ->
                    val item = uiState.searchNoticeList[index]
                    NoticeCard(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        author = item.memberInfoRes.name,
                        date = formatLocalDateTime(item.createdAt),
                        title = item.title,
                        content = item.content,
                        images = item.noticeFileRes
                            .filter { it.fileType == NoticeFileType.IMAGE }
                            .map {
                                it.fileUrl
                            }.toImmutableList(),
                        onLinkClick = { url ->
                            uriHandler.openUri(url)
                        },
                    )
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
