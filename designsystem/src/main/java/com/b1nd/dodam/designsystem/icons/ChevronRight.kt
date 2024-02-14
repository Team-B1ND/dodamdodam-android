package com.b1nd.dodam.designsystem.icons

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
private fun VectorPreview() {
    Image(ChevronRight, null)
}

private var _vector: ImageVector? = null

public val ChevronRight: ImageVector
get() {
    if (_vector != null) {
        return _vector!!
    }
    _vector = ImageVector.Builder(
            name = "Chevron right",
    defaultWidth = 48.dp,
    defaultHeight = 48.dp,
    viewportWidth = 48f,
    viewportHeight = 48f
    ).apply {
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
            moveTo(35.5f, 23.998f)
            curveTo(35.4919f, 23.2298f, 35.2047f, 22.5678f, 34.5961f, 21.9821f)
            lineTo(16.9859f, 4.74198f)
            curveTo(16.473f, 4.2481f, 15.8617f, 4f, 15.1232f, 4f)
            curveTo(13.6689f, 4f, 12.5f, 5.1414f, 12.5f, 6.6091f)
            curveTo(12.5f, 7.3245f, 12.7912f, 7.9865f, 13.2954f, 8.5007f)
            lineTo(29.1941f, 23.9939f)
            lineTo(13.2954f, 39.4953f)
            curveTo(12.7953f, 40.0054f, 12.5f, 40.6529f, 12.5f, 41.3909f)
            curveTo(12.5f, 42.8546f, 13.6689f, 44f, 15.1232f, 44f)
            curveTo(15.8535f, 44f, 16.473f, 43.752f, 16.9859f, 43.2395f)
            lineTo(34.5961f, 26.0138f)
            curveTo(35.2088f, 25.4097f, 35.5f, 24.7621f, 35.5f, 23.998f)
            close()
        }
    }.build()
    return _vector!!
}
