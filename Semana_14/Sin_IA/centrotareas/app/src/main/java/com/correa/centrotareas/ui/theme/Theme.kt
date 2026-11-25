package com.correa.centrotareas.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = AzulCentro,
    onPrimary = Color.White,
    primaryContainer = AzulCentroClaro,
    onPrimaryContainer = AzulCentroOscuro,
    secondary = Color(0xFF4A6274),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD6E4F0),
    onSecondaryContainer = Color(0xFF061E2C),
    tertiary = VerdeExito,
    onTertiary = Color.White,
    tertiaryContainer = VerdeExitoFondo,
    onTertiaryContainer = VerdeExito,
    error = RojoError,
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    background = Color(0xFFF7F9FC),
    onBackground = Color(0xFF1A1C1E),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFE1E7EF),
    onSurfaceVariant = Color(0xFF41484D),
    outline = Color(0xFF71787E)
)

private val DarkColorScheme = darkColorScheme(
    primary = AzulCentroSuave,
    onPrimary = AzulCentroOscuro,
    primaryContainer = Color(0xFF00497F),
    onPrimaryContainer = AzulCentroClaro,
    secondary = Color(0xFFB3C9DA),
    onSecondary = Color(0xFF1D3444),
    secondaryContainer = Color(0xFF344B5C),
    onSecondaryContainer = Color(0xFFD6E4F0),
    tertiary = VerdeExitoOscuro,
    onTertiary = Color(0xFF00391A),
    tertiaryContainer = VerdeExitoFondoOscuro,
    onTertiaryContainer = VerdeExitoOscuro,
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC),
    background = Color(0xFF111418),
    onBackground = Color(0xFFE2E2E6),
    surface = Color(0xFF191C20),
    onSurface = Color(0xFFE2E2E6),
    surfaceVariant = Color(0xFF41484D),
    onSurfaceVariant = Color(0xFFC1C7CE),
    outline = Color(0xFF8B9198)
)

@Composable
fun CentrotareasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
