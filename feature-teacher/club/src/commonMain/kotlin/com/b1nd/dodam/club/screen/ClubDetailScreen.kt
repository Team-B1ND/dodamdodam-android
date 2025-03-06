package com.b1nd.dodam.club.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.club.model.ClubPendingUiState
import com.b1nd.dodam.club.model.ClubUiState
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.AvatarSize
import com.b1nd.dodam.designsystem.component.DodamAvatar
import com.b1nd.dodam.designsystem.component.DodamDivider
import com.b1nd.dodam.designsystem.component.DodamEmpty
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.effect.shimmerEffect
import com.mikepenz.markdown.coil3.Coil3ImageTransformerImpl
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.model.DefaultMarkdownColors
import com.mikepenz.markdown.model.DefaultMarkdownTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ClubDetailScreen(state: ClubUiState, popBackStack: () -> Unit) {
    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState,
    )
    val columnScrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DodamTopAppBar(
                title = "",
                modifier = Modifier.statusBarsPadding(),
                onBackClick = popBackStack,
            )
        },
        containerColor = DodamTheme.colors.backgroundNeutral,
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 16.dp)) {
            BottomSheetScaffold(
                sheetContent = {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        LazyColumn(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.666f)) {
                            when (val data = state.clubPendingUiState) {
                                ClubPendingUiState.Error -> {}
                                ClubPendingUiState.Loading -> {
                                    item {
                                        Box(modifier = Modifier.padding(top = 2.dp).width(50.dp).height(20.dp).background(brush = shimmerEffect()))
                                        DodamLoadingClubMember(isFirst = true)
                                        DodamLoadingClubMember()
                                        DodamLoadingClubMember()
                                    }
                                }
                                is ClubPendingUiState.Success -> {
                                    item {
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Row {
                                            Text(
                                                text = "멤버현황",
                                                style = DodamTheme.typography.headlineBold(),
                                                color = DodamTheme.colors.labelNormal,
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            Text(
                                                text = "멤버 수 : ${data.detailClubMember.clubMember.size}",
                                                style = DodamTheme.typography.headlineBold(),
                                                color = DodamTheme.colors.labelNormal,
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                    }
                                    items(data.detailClubMember.clubMember.size) { index ->
                                        DodamClubMember(
                                            image = data.detailClubMember.clubMember[index].profileImage,
                                            permission = data.detailClubMember.clubMember[index].permissions.toString(),
                                            name = data.detailClubMember.clubMember[index].name,
                                            grade = data.detailClubMember.clubMember[index].grade,
                                            room = data.detailClubMember.clubMember[index].room,
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                    }
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxSize(),
                scaffoldState = scaffoldState,
                sheetPeekHeight = 325.dp,
                sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                sheetContainerColor = DodamTheme.colors.backgroundNormal,
                sheetTonalElevation = 0.dp,
                sheetShadowElevation = 0.dp,
                sheetDragHandle = {
                    Box(
                        modifier = Modifier.padding(top = 16.dp).width(64.dp).height(6.dp)
                            .background(
                                color = DodamTheme.colors.fillAlternative,
                                shape = DodamTheme.shapes.extraSmall,
                            ),
                    )
                },
                sheetSwipeEnabled = true,
                containerColor = DodamTheme.colors.backgroundNeutral,
                content = {
                    when (val data = state.clubPendingUiState) {
                        ClubPendingUiState.Error -> {
                            Box(modifier = Modifier.fillMaxSize()){
                                DodamEmpty(
                                    modifier = Modifier.align(Alignment.TopCenter).padding(top = 16.dp),
                                    onClick = popBackStack,
                                    title = "에러가 발생했어요!",
                                    buttonText = "뒤로가기"
                                )
                            }
                        }

                        ClubPendingUiState.Loading -> {
                            Column {
                                Box(
                                    modifier = Modifier.width(160.dp).height(14.dp)
                                        .background(brush = shimmerEffect()),
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier.width(280.dp).height(22.dp)
                                        .background(brush = shimmerEffect()),
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier.width(200.dp).height(16.dp)
                                        .background(brush = shimmerEffect()),
                                )
                                DodamDivider(modifier = Modifier.padding(vertical = 20.dp))
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier.width(160.dp).height(20.dp)
                                        .background(brush = shimmerEffect()),
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier.fillMaxWidth().height(18.dp)
                                        .background(brush = shimmerEffect()),
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier.width(200.dp).height(18.dp)
                                        .background(brush = shimmerEffect()),
                                )
                            }
                        }

                        is ClubPendingUiState.Success -> {
                            Box {
                                Column(
                                    modifier = Modifier.verticalScroll(columnScrollState),
                                ) {
                                    Text(
                                        text = "${data.detailClubMember.club.type.type} • ${data.detailClubMember.club.subject}",
                                        style = DodamTheme.typography.labelMedium(),
                                        color = DodamTheme.colors.labelAlternative,
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = data.detailClubMember.club.name,
                                        style = DodamTheme.typography.heading1Bold(),
                                        color = DodamTheme.colors.labelNormal,
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = data.detailClubMember.club.description,
                                        style = DodamTheme.typography.body1Medium(),
                                        color = DodamTheme.colors.labelNormal,
                                    )
                                    DodamDivider(modifier = Modifier.padding(vertical = 20.dp))
                                    Text(
                                        text = "설명",
                                        style = DodamTheme.typography.headlineBold(),
                                        color = DodamTheme.colors.labelNormal,
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Markdown(
                                        content = data.detailClubMember.club.description,
                                        colors = DefaultMarkdownColors(
                                            text = DodamTheme.colors.labelNeutral,
                                            codeText = DodamTheme.colors.labelNeutral,
                                            inlineCodeText = DodamTheme.colors.labelNeutral,
                                            linkText = DodamTheme.colors.labelNeutral,
                                            codeBackground = DodamTheme.colors.labelNeutral,
                                            inlineCodeBackground = DodamTheme.colors.labelNeutral,
                                            dividerColor = DodamTheme.colors.labelNeutral,
                                        ),
                                        typography = DefaultMarkdownTypography(
                                            h1 = DodamTheme.typography.title1Bold(),
                                            h2 = DodamTheme.typography.title2Bold(),
                                            h3 = DodamTheme.typography.title3Bold(),
                                            h4 = DodamTheme.typography.heading1Bold(),
                                            h5 = DodamTheme.typography.heading2Bold(),
                                            h6 = DodamTheme.typography.headlineBold(),
                                            text = DodamTheme.typography.body1Medium(),
                                            code = DodamTheme.typography.body1Medium(),
                                            quote = DodamTheme.typography.body1Medium(),
                                            paragraph = DodamTheme.typography.body1Medium(),
                                            ordered = DodamTheme.typography.body1Medium(),
                                            bullet = DodamTheme.typography.body1Medium(),
                                            list = DodamTheme.typography.body1Medium(),
                                        ),
                                        imageTransformer = Coil3ImageTransformerImpl,
                                    )
                                    Spacer(modifier = Modifier.height(400.dp))
                                }
                            }
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun DodamClubMember(modifier: Modifier = Modifier, image: String? = "", permission: String = "", name: String, grade: Int, room: Int) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DodamAvatar(
            model = image,
            avatarSize = AvatarSize.Large,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = name,
                    style = DodamTheme.typography.body1Medium(),
                    color = DodamTheme.colors.labelNormal,
                )
                Spacer(modifier = Modifier.width(4.dp))
                if (permission == "CLUB_LEADER") {
                    // DDS 에 Crown 추가하면 아이콘 Crown 으로 바꿀 예정입니다.
                    Image(
                        modifier = Modifier.size(16.dp),
                        imageVector = DodamIcons.Crown.value,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(DodamTheme.colors.statusCautionary),
                    )
                }
            }

            Text(
                text = "$grade-$room",
                style = DodamTheme.typography.body2Medium(),
                color = DodamTheme.colors.labelAlternative,
            )
        }
    }
}

@Composable
private fun DodamLoadingClubMember(modifier: Modifier = Modifier, isFirst: Boolean = false) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.size(34.5.dp)
                .background(shape = CircleShape, brush = shimmerEffect()),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.width(52.dp).height(20.dp)
                        .background(brush = shimmerEffect()),
                )
                Spacer(modifier = Modifier.width(4.dp))
                if (isFirst) {
                    Box(
                        modifier = Modifier.size(20.dp)
                            .background(shape = CircleShape, brush = shimmerEffect()),
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Box(modifier = Modifier.width(32.dp).height(18.dp).background(brush = shimmerEffect()))
        }
    }
}
