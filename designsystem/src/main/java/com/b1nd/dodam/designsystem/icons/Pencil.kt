package com.b1nd.dodam.designsystem.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Pencil: ImageVector
    get() {
        if (_vector != null) {
            return _vector!!
        }
        _vector = Builder(name = "Pencil", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFFFF822D)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(10.397f, 4.7412f)
                lineTo(13.2157f, 6.3252f)
                lineTo(14.8164f, 9.1606f)
                lineTo(5.8559f, 18.1211f)
                lineTo(2.8173f, 16.5146f)
                lineTo(1.4365f, 13.7017f)
                lineTo(10.397f, 4.7412f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFFCE7C)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(0.8682f, 17.5405f)
                lineTo(1.232f, 18.3603f)
                lineTo(2.0164f, 18.6887f)
                lineTo(5.8479f, 18.1104f)
                lineTo(1.4464f, 13.709f)
                lineTo(0.8682f, 17.5405f)
                close()
            }
            path(fill = SolidColor(Color(0xFF402A32)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(0.6641f, 18.8933f)
                lineTo(0.8682f, 17.541f)
                lineTo(2.0164f, 18.6892f)
                lineTo(0.6641f, 18.8933f)
                close()
            }
            path(fill = SolidColor(Color(0xFFF92F60)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(13.9226f, 1.2157f)
                curveTo(14.4108f, 0.7276f, 15.2022f, 0.7276f, 15.6904f, 1.2157f)
                lineTo(18.342f, 3.8674f)
                curveTo(18.8302f, 4.3555f, 18.8302f, 5.147f, 18.342f, 5.6351f)
                lineTo(16.1323f, 7.8449f)
                lineTo(13.3028f, 6.4298f)
                lineTo(11.7129f, 3.4254f)
                lineTo(13.9226f, 1.2157f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD3D3D3)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(11.7125f, 3.4248f)
                lineTo(16.132f, 7.8442f)
                lineTo(14.8061f, 9.17f)
                lineTo(10.3867f, 4.7506f)
                lineTo(11.7125f, 3.4248f)
                close()
            }
        }
        .build()
        return _vector!!
    }

private var _vector: ImageVector? = null
