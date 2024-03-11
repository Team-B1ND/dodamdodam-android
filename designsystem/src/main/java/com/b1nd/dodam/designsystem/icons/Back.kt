package com.b1nd.dodam.designsystem.icons

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun VectorPreview() {
    Image(Back, null)
}

private var vector: ImageVector? = null

public val Back: ImageVector
    get() {
        if (vector != null) {
            return vector!!
        }
        vector = ImageVector.Builder(
            name = "Back",
            defaultWidth = 48.dp,
            defaultHeight = 48.dp,
            viewportWidth = 48f,
            viewportHeight = 48f,
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
                    pathFillType = PathFillType.NonZero,
                ) {
                    moveTo(4f, 23.9675f)
                    curveTo(4f, 24.5907f, 4.2732f, 25.195f, 4.7446f, 25.6463f)
                    lineTo(18.4385f, 39.2239f)
                    curveTo(18.9286f, 39.6974f, 19.473f, 39.9312f, 20.0573f, 39.9312f)
                    curveTo(21.3438f, 39.9312f, 22.2956f, 39.0179f, 22.2956f, 37.7751f)
                    curveTo(22.2956f, 37.1354f, 22.034f, 36.5624f, 21.6233f, 36.1751f)
                    lineTo(16.9722f, 31.4988f)
                    lineTo(9.23543f, 24.4802f)
                    lineTo(8.49665f, 25.7989f)
                    lineTo(15.6798f, 26.2188f)
                    horizontalLineTo(40.941f)
                    curveTo(42.3047f, 26.2188f, 43.2318f, 25.2961f, 43.2318f, 23.9675f)
                    curveTo(43.2318f, 22.6388f, 42.3047f, 21.7161f, 40.941f, 21.7161f)
                    horizontalLineTo(15.6798f)
                    lineTo(8.49665f, 22.136f)
                    lineTo(9.23543f, 23.4754f)
                    lineTo(16.9722f, 16.4362f)
                    lineTo(21.6233f, 11.7561f)
                    curveTo(22.034f, 11.3482f, 22.2956f, 10.7958f, 22.2956f, 10.1561f)
                    curveTo(22.2956f, 8.9132f, 21.3438f, 8f, 20.0573f, 8f)
                    curveTo(19.473f, 8f, 18.9286f, 8.217f, 18.4045f, 8.7412f)
                    lineTo(4.7446f, 22.2886f)
                    curveTo(4.2732f, 22.7399f, 4f, 23.3442f, 4f, 23.9675f)
                    close()
                }
            }
        }.build()
        return vector!!
    }
