package com.b1nd.dodam.designsystem.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _Home: ImageVector? = null

public val Home: ImageVector
    get() {
        if (_Home != null) {
            return _Home!!
        }
        _Home = ImageVector.Builder(
            name = "Home",
            defaultWidth = 48.dp,
            defaultHeight = 48.dp,
            viewportWidth = 48f,
            viewportHeight = 48f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFF000000)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(22.1259f, 5.49927f)
                    curveTo(23.2216f, 4.6227f, 24.7784f, 4.6227f, 25.8741f, 5.4993f)
                    lineTo(42.8741f, 19.0993f)
                    curveTo(43.5857f, 19.6686f, 44f, 20.5305f, 44f, 21.4419f)
                    verticalLineTo(41f)
                    curveTo(44f, 42.6569f, 42.6569f, 44f, 41f, 44f)
                    horizontalLineTo(29.5f)
                    curveTo(28.6716f, 44f, 28f, 43.3284f, 28f, 42.5f)
                    verticalLineTo(33.5f)
                    curveTo(28f, 32.6716f, 27.3284f, 32f, 26.5f, 32f)
                    horizontalLineTo(21.5f)
                    curveTo(20.6716f, 32f, 20f, 32.6716f, 20f, 33.5f)
                    verticalLineTo(42.5f)
                    curveTo(20f, 43.3284f, 19.3284f, 44f, 18.5f, 44f)
                    horizontalLineTo(7f)
                    curveTo(5.3431f, 44f, 4f, 42.6569f, 4f, 41f)
                    verticalLineTo(21.4419f)
                    curveTo(4f, 20.5305f, 4.4143f, 19.6686f, 5.1259f, 19.0993f)
                    lineTo(22.1259f, 5.49927f)
                    close()
                }
            }
        }.build()
        return _Home!!
    }

