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
    Image(Cancel, null)
}

private var vector: ImageVector? = null

public val Cancel: ImageVector
    get() {
        if (vector != null) {
            return vector!!
        }
        vector = ImageVector.Builder(
            name = "Cancel",
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
                    moveTo(43.7117f, 23.9938f)
                    curveTo(43.7117f, 34.9318f, 34.7314f, 43.9876f, 23.8541f, 43.9876f)
                    curveTo(12.996f, 43.9876f, 4f, 34.9318f, 4f, 23.9938f)
                    curveTo(4f, 13.0399f, 12.9803f, 4f, 23.8384f, 4f)
                    curveTo(34.7156f, 4f, 43.7117f, 13.0399f, 43.7117f, 23.9938f)
                    close()
                    moveTo(27.8662f, 15.6034f)
                    lineTo(24.0744f, 21.2898f)
                    horizontalLineTo(23.9575f)
                    lineTo(20.0941f, 15.5841f)
                    curveTo(19.5325f, 14.7677f, 19.0893f, 14.4742f, 18.299f, 14.4742f)
                    curveTo(17.2872f, 14.4742f, 16.4896f, 15.2353f, 16.4896f, 16.2015f)
                    curveTo(16.4896f, 16.7094f, 16.6167f, 17.0606f, 17.0058f, 17.5789f)
                    lineTo(21.4904f, 23.7592f)
                    lineTo(16.7625f, 30.2852f)
                    curveTo(16.4399f, 30.7401f, 16.3127f, 31.0912f, 16.3127f, 31.5673f)
                    curveTo(16.3127f, 32.5143f, 17.0438f, 33.2153f, 18.0557f, 33.2153f)
                    curveTo(18.8145f, 33.2153f, 19.2104f, 32.9343f, 19.8193f, 32.0702f)
                    lineTo(23.7491f, 26.3803f)
                    horizontalLineTo(23.8503f)
                    lineTo(27.7367f, 32.0702f)
                    curveTo(28.3125f, 32.9273f, 28.7504f, 33.2153f, 29.5003f, 33.2153f)
                    curveTo(30.5594f, 33.2153f, 31.3255f, 32.5143f, 31.3255f, 31.5004f)
                    curveTo(31.3255f, 31.0243f, 31.1791f, 30.6607f, 30.8408f, 30.2218f)
                    lineTo(26.1794f, 23.8385f)
                    lineTo(30.8723f, 17.4325f)
                    curveTo(31.2298f, 16.9776f, 31.357f, 16.5789f, 31.357f, 16.138f)
                    curveTo(31.357f, 15.1877f, 30.6066f, 14.4742f, 29.5949f, 14.4742f)
                    curveTo(28.8449f, 14.4742f, 28.3791f, 14.7781f, 27.8662f, 15.6034f)
                    close()
                }
            }
        }.build()
        return vector!!
    }
