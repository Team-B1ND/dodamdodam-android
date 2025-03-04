package com.b1nd.dodam.ui.icons

/*
* Converted using https://composables.com/svgtocompose
*/

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val UpDownArrow: ImageVector
    get() {
        if (upDownArrow != null) {
            return upDownArrow!!
        }
        upDownArrow = ImageVector.Builder(
            name = "Frame 831",
            defaultWidth = 9.dp,
            defaultHeight = 12.dp,
            viewportWidth = 9f,
            viewportHeight = 12f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFF0083F0)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd,
            ) {
                moveTo(0.178982f, 3.97949f)
                lineTo(4.0679f, 0.175093f)
                curveTo(4.30650f, -0.05840f, 4.69350f, -0.05840f, 4.93210f, 0.17510f)
                lineTo(8.82102f, 3.97949f)
                curveTo(9.05970f, 4.21290f, 9.05970f, 4.59150f, 8.8210f, 4.82490f)
                curveTo(8.58240f, 5.05840f, 8.19550f, 5.05840f, 7.95680f, 4.82490f)
                lineTo(4.5f, 1.44322f)
                lineTo(1.04319f, 4.82491f)
                curveTo(0.80450f, 5.05840f, 0.41760f, 5.05840f, 0.1790f, 4.82490f)
                curveTo(-0.05970f, 4.59150f, -0.05970f, 4.21290f, 0.1790f, 3.97950f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF0083F0)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd,
            ) {
                moveTo(8.82102f, 8.02051f)
                lineTo(4.9321f, 11.8249f)
                curveTo(4.69350f, 12.05840f, 4.30650f, 12.05840f, 4.06790f, 11.82490f)
                lineTo(0.178983f, 8.02051f)
                curveTo(-0.05970f, 7.78710f, -0.05970f, 7.40850f, 0.1790f, 7.17510f)
                curveTo(0.41760f, 6.94160f, 0.80450f, 6.94160f, 1.04320f, 7.17510f)
                lineTo(4.5f, 10.5568f)
                lineTo(7.95681f, 7.17509f)
                curveTo(8.19550f, 6.94160f, 8.58240f, 6.94160f, 8.8210f, 7.17510f)
                curveTo(9.05970f, 7.40850f, 9.05970f, 7.78710f, 8.8210f, 8.02050f)
                close()
            }
        }.build()
        return upDownArrow!!
    }

private var upDownArrow: ImageVector? = null
