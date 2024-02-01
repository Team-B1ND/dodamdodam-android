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
    Image(Error, null)
}

private var vector: ImageVector? = null

public val Error: ImageVector
    get() {
        if (vector != null) {
            return vector!!
        }
        vector = ImageVector.Builder(
            name = "Error",
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
                    moveTo(43.2961f, 23.9939f)
                    curveTo(43.2961f, 34.9318f, 34.41f, 43.9877f, 23.6464f, 43.9877f)
                    curveTo(12.9019f, 43.9877f, 4f, 34.9318f, 4f, 23.9939f)
                    curveTo(4f, 13.04f, 12.8863f, 4f, 23.6308f, 4f)
                    curveTo(34.3942f, 4f, 43.2961f, 13.04f, 43.2961f, 23.9939f)
                    close()
                    moveTo(21.3177f, 31.7946f)
                    curveTo(21.3177f, 33.0585f, 22.3751f, 34.0052f, 23.6532f, 34.0052f)
                    curveTo(24.9279f, 34.0052f, 25.9787f, 33.0744f, 25.9787f, 31.7946f)
                    curveTo(25.9787f, 30.5253f, 24.9435f, 29.5753f, 23.6532f, 29.5753f)
                    curveTo(22.3596f, 29.5753f, 21.3177f, 30.5378f, 21.3177f, 31.7946f)
                    close()
                    moveTo(21.5965f, 15.7924f)
                    lineTo(21.8643f, 25.5575f)
                    curveTo(21.9004f, 26.7053f, 22.5341f, 27.3362f, 23.6532f, 27.3362f)
                    curveTo(24.7308f, 27.3362f, 25.3613f, 26.7245f, 25.3973f, 25.5506f)
                    lineTo(25.6807f, 15.8082f)
                    curveTo(25.7169f, 14.6256f, 24.8446f, 13.7836f, 23.6308f, 13.7836f)
                    curveTo(22.4048f, 13.7836f, 21.5604f, 14.6097f, 21.5965f, 15.7924f)
                    close()
                }
            }
        }.build()
        return vector!!
    }
