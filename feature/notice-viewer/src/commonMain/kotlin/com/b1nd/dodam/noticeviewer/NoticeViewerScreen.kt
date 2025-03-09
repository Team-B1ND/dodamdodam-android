package com.b1nd.dodam.noticeviewer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculateCentroidSize
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateRotation
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.b1nd.dodam.data.notice.model.NoticeFile
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.ui.component.DodamMenuDialog
import com.b1nd.dodam.ui.component.DodamMenuItem
import com.b1nd.dodam.ui.component.DodamMenuItemColor
import com.b1nd.dodam.ui.util.LocalFileDownloader
import kotlin.math.abs
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun NoticeViewerScreen(popBackStack: () -> Unit, startIndex: Int, images: ImmutableList<NoticeFile>) {
    var topBarVisible by remember { mutableStateOf(true) }
    val pagerState = rememberPagerState(initialPage = startIndex, pageCount = { images.size })
    val fileDownloader = LocalFileDownloader.current
    var showFileDownloadDialog by remember { mutableStateOf(false) }

    if (showFileDownloadDialog) {
        Dialog(
            onDismissRequest = {
                showFileDownloadDialog = false
            },
            content = {
                DodamMenuDialog(
                    items = persistentListOf(
                        DodamMenuItem(
                            item = "해당 이미지 다운로드",
                            color = DodamMenuItemColor.Normal,
                            onClickItem = {
                                val item = images[pagerState.currentPage]
                                fileDownloader.downloadFile(
                                    fileName = item.fileName,
                                    fileUrl = item.fileUrl,
                                )
                                showFileDownloadDialog = false
                            },
                        ),
                        DodamMenuItem(
                            item = "전체 이미지 다운로드",
                            color = DodamMenuItemColor.Normal,
                            onClickItem = {
                                for (image in images) {
                                    fileDownloader.downloadFile(
                                        fileName = image.fileName,
                                        fileUrl = image.fileUrl,
                                    )
                                }
                                showFileDownloadDialog = false
                            },
                        ),
                    ),
                )
            },
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DodamTheme.colors.staticBlack),
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 8.dp,
            pageContent = { page ->
                PinchToZoomView(
                    model = images[page].fileUrl,
                    onTab = {
                        topBarVisible = !topBarVisible
                    },
                )
            },
        )

        if (topBarVisible) {
            Row(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(
                        color = DodamTheme.colors.staticBlack.copy(alpha = 0.8f),
                    )
                    .padding(
                        vertical = 6.dp,
                        horizontal = 4.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                NoticeViewerIconButton(
                    onClick = popBackStack,
                    imageVector = DodamIcons.ArrowLeft.value,
                )

                NoticeViewerIconButton(
                    onClick = {
                        showFileDownloadDialog = true
                    },
                    imageVector = DodamIcons.Download.value,
                )
            }
        }
    }
}

@Composable
private fun NoticeViewerIconButton(modifier: Modifier = Modifier, imageVector: ImageVector, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .size(48.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable(
                    onClick = onClick,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberBounceIndication(DodamTheme.shapes.medium),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = imageVector,
                contentDescription = "Icon Button",
                tint = DodamTheme.colors.staticWhite,
            )
        }
    }
}

@Composable
private fun PinchToZoomView(modifier: Modifier = Modifier, model: Any, onTab: () -> Unit) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val minScale = 1f
    val maxScale = 4f

    var initialOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    val slowMovement = 0.5f

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectCustomTransformGestures(
                    consume = false,
                    onGesture = { _, pan, zoom, _, _, _ ->
                        val newScale = scale * zoom
                        scale = newScale.coerceIn(minScale, maxScale)

                        val centerX = size.width / 2
                        val centerY = size.height / 2
                        val offsetXChange = (centerX - offsetX) * (newScale / scale - 1)
                        val offsetYChange = (centerY - offsetY) * (newScale / scale - 1)

                        val maxOffsetX = (size.width / 2) * (scale - 1)
                        val minOffsetX = -maxOffsetX
                        val maxOffsetY = (size.height / 2) * (scale - 1)
                        val minOffsetY = -maxOffsetY

                        if (scale * zoom <= maxScale) {
                            offsetX = (offsetX + pan.x * scale * slowMovement + offsetXChange)
                                .coerceIn(minOffsetX, maxOffsetX)
                            offsetY = (offsetY + pan.y * scale * slowMovement + offsetYChange)
                                .coerceIn(minOffsetY, maxOffsetY)
                        }

                        if (pan != Offset(0f, 0f) && initialOffset == Offset(0f, 0f)) {
                            initialOffset = Offset(offsetX, offsetY)
                        }
                    },
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onTab()
                    },
                    onDoubleTap = {
                        if (scale != 1f) {
                            scale = 1f
                            offsetX = initialOffset.x
                            offsetY = initialOffset.y
                        } else {
                            scale = 2f
                        }
                    },
                )
            }
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationX = offsetX
                translationY = offsetY
            },
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = model,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
    }
}

private suspend fun PointerInputScope.detectCustomTransformGestures(
    panZoomLock: Boolean = false,
    consume: Boolean = true,
    pass: PointerEventPass = PointerEventPass.Main,
    onGestureStart: (PointerInputChange) -> Unit = {},
    onGesture: (
        centroid: Offset,
        pan: Offset,
        zoom: Float,
        rotation: Float,
        mainPointer: PointerInputChange,
        changes: List<PointerInputChange>,
    ) -> Unit,
    onGestureEnd: (PointerInputChange) -> Unit = {},
) {
    awaitEachGesture {
        var rotation = 0f
        var zoom = 1f
        var pan = Offset.Zero
        var pastTouchSlop = false
        val touchSlop = viewConfiguration.touchSlop
        var lockedToPanZoom = false

        // Wait for at least one pointer to press down, and set first contact position
        val down: PointerInputChange = awaitFirstDown(
            requireUnconsumed = false,
            pass = pass,
        )
        onGestureStart(down)

        var pointer = down
        // Main pointer is the one that is down initially
        var pointerId = down.id

        do {
            val event = awaitPointerEvent(pass = pass)

            // If any position change is consumed from another PointerInputChange
            // or pointer count requirement is not fulfilled
            val canceled =
                event.changes.any { it.isConsumed }

            if (!canceled) {
                // Get pointer that is down, if first pointer is up
                // get another and use it if other pointers are also down
                // event.changes.first() doesn't return same order
                val pointerInputChange =
                    event.changes.firstOrNull { it.id == pointerId }
                        ?: event.changes.first()

                // Next time will check same pointer with this id
                pointerId = pointerInputChange.id
                pointer = pointerInputChange

                val zoomChange = event.calculateZoom()
                val rotationChange = event.calculateRotation()
                val panChange = event.calculatePan()

                if (!pastTouchSlop) {
                    zoom *= zoomChange
                    rotation += rotationChange
                    pan += panChange

                    val centroidSize = event.calculateCentroidSize(useCurrent = false)
                    val zoomMotion = abs(1 - zoom) * centroidSize
                    val rotationMotion =
                        abs(rotation * kotlin.math.PI.toFloat() * centroidSize / 180f)
                    val panMotion = pan.getDistance()

                    if (zoomMotion > touchSlop ||
                        rotationMotion > touchSlop ||
                        panMotion > touchSlop
                    ) {
                        pastTouchSlop = true
                        lockedToPanZoom = panZoomLock && rotationMotion < touchSlop
                    }
                }

                if (pastTouchSlop) {
                    val centroid = event.calculateCentroid(useCurrent = false)
                    val effectiveRotation = if (lockedToPanZoom) 0f else rotationChange
                    if (effectiveRotation != 0f ||
                        zoomChange != 1f ||
                        panChange != Offset.Zero
                    ) {
                        onGesture(
                            centroid,
                            panChange,
                            zoomChange,
                            effectiveRotation,
                            pointer,
                            event.changes,
                        )
                    }

                    if (consume) {
                        event.changes.forEach {
                            if (it.positionChanged()) {
                                it.consume()
                            }
                        }
                    }
                }
            }
        } while (!canceled && event.changes.any { it.pressed })
        onGestureEnd(pointer)
    }
}
