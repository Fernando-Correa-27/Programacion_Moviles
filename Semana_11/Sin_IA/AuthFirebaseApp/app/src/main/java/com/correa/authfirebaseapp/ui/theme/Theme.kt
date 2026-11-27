package com.correa.authfirebaseapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkBrandPurple,
    onPrimary = Color(0xFF2B1B61),
    secondary = DarkBrandBlue,
    onSecondary = Color(0xFF0B1E3B),
    tertiary = DarkBrandPurple,
    onTertiary = Color(0xFF24104B),
    background = DarkAppBackground,
    onBackground = DarkAppText,
    surface = DarkAppSurface,
    onSurface = DarkAppText,
    surfaceVariant = Color(0xFF273443),
    onSurfaceVariant = DarkAppMutedText,
    outline = DarkAppOutline
)

private val LightColorScheme = lightColorScheme(
    primary = BrandNavy,
    onPrimary = Color.White,
    secondary = BrandBlue,
    onSecondary = Color.Black,
    tertiary = BrandPurple,
    onTertiary = Color.White,
    background = AppBackground,
    onBackground = AppText,
    surface = AppSurface,
    onSurface = AppText,
    surfaceVariant = Color(0xFFE9EDF4),
    onSurfaceVariant = AppMutedText,
    outline = AppOutline
)

@Composable
fun AuthFirebaseAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
