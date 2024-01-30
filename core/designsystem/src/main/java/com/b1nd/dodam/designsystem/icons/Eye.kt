package com.b1nd.dodam.designsystem.icons

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun VectorPreview() {
    Image(Eye, null)
}

private var _Eye: ImageVector? = null

public val Eye: ImageVector
    get() {
        if (_Eye != null) {
            return _Eye!!
        }
        _Eye = ImageVector.Builder(
            name = "Eye",
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
                    moveTo(23.8924f, 35.9998f)
                    curveTo(35.7546f, 35.9998f, 43.7806f, 26.4575f, 43.7806f, 23.5097f)
                    curveTo(43.7806f, 20.5526f, 35.7425f, 11.0249f, 23.8924f, 11.0249f)
                    curveTo(12.1922f, 11.0249f, 4f, 20.5526f, 4f, 23.5097f)
                    curveTo(4f, 26.4575f, 12.1922f, 35.9998f, 23.8924f, 35.9998f)
                    close()
                    moveTo(23.8976f, 31.4838f)
                    curveTo(19.4267f, 31.4838f, 15.8522f, 27.8554f, 15.8522f, 23.5124f)
                    curveTo(15.8522f, 19.0526f, 19.4267f, 15.5409f, 23.8976f, 15.5409f)
                    curveTo(28.3326f, 15.5409f, 31.931f, 19.0553f, 31.931f, 23.5124f)
                    curveTo(31.931f, 27.8554f, 28.3326f, 31.4838f, 23.8976f, 31.4838f)
                    close()
                    moveTo(23.8924f, 26.4797f)
                    curveTo(25.5394f, 26.4797f, 26.8955f, 25.1397f, 26.8955f, 23.5176f)
                    curveTo(26.8955f, 21.8809f, 25.5394f, 20.5357f, 23.8924f, 20.5357f)
                    curveTo(22.2385f, 20.5357f, 20.8878f, 21.8809f, 20.8878f, 23.5176f)
                    curveTo(20.8878f, 25.1397f, 22.2385f, 26.4797f, 23.8924f, 26.4797f)
                    close()
                }
            }
        }.build()
        return _Eye!!
    }
