package com.b1nd.dodam.ui.component.modifier


import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.NativePaint

import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * Seugi DropShadow
 *
 * @param type: the size of the shadow according to the design system.
 * @param modifier: the Modifier to be applied to this drop shadow
 */
fun Modifier.dropShadow(
    blur: Dp,
    offsetY: Dp,
    color: Color,
    modifier: Modifier = Modifier
) = then(
    modifier.drawBehind {
        drawIntoCanvas { canvas ->

            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = 0f
            val leftPixel = (0f - spreadPixel) + 0f // offsetX
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = size.width + spreadPixel
            val bottomPixel = size.height + spreadPixel

            frameworkPaint.color = color.toArgb()

            if (blur != 0.dp) {
                (frameworkPaint as? NativePaint)?.setMaskFilter((blur.toPx()))
            }

            canvas.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = 0f,
                radiusY = 0f,
                paint = paint,
            )
        }
    },
)
