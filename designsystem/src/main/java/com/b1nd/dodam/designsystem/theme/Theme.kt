package com.b1nd.dodam.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme =
    darkColorScheme(
        primary = Blue500,
        onPrimary = White,
        secondary = Gray800,
        tertiary = Gray600,
        surface = Black,
        onSurface = Gray100,
        background = Black,
        onBackground = White,
        error = Red500,
        onError = White,
        surfaceVariant = Gray900,
        onSurfaceVariant = White,
        outline = Gray700,
        inverseSurface = Color(0xFF222222),
        inverseOnSurface = Color(0xFF6C6C6C),
    )

private val LightColorScheme =
    lightColorScheme(
        primary = Blue500,
        onPrimary = White,
        secondary = Gray200,
        tertiary = Gray400,
        surface = Color(0xFFF2F5F8),
        onSurface = Gray900,
        background = White,
        onBackground = Black,
        error = Red500,
        onError = White,
        surfaceVariant = White,
        onSurfaceVariant = Gray900,
        outline = Gray300,
        inverseSurface = Color(0xFFC6DFF3),
        inverseOnSurface = Color(0xFF72ADDC),

        /* Other default colors to override
        background = Color(0xFFFFFBFE),
        surface = Color(0xFFFFFBFE),
        onPrimary = Color.White,
        onSecondary = Color.White,
        onTertiary = Color.White,
        onBackground = Color(0xFF1C1B1F),
        onSurface = Color(0xFF1C1B1F),
         */
    )

@Composable
fun DodamTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> DarkColorScheme

            else -> LightColorScheme
        }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Pretendard,
        content = content,
    )
}
