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
    inversePrimary = AzulCentroSuave,
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
    background = Color(0xFFF4F7FB),
    onBackground = Color(0xFF1A1C1E),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFE1E7EF),
    onSurfaceVariant = Color(0xFF41484D),
    surfaceTint = AzulCentro,
    inverseSurface = Color(0xFF2F3033),
    inverseOnSurface = Color(0xFFF1F0F4),
    outline = Color(0xFF71787E),
    outlineVariant = Color(0xFFC9D8EA),
    scrim = Color.Black,
    surfaceBright = Color(0xFFFFFFFF),
    surfaceDim = Color(0xFFDDE3EA),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFFFFFF),
    surfaceContainer = Color(0xFFEEF4FB),
    surfaceContainerHigh = Color(0xFFE8EFF8),
    surfaceContainerHighest = Color(0xFFE2EAF4)
)

private val DarkColorScheme = darkColorScheme(
    primary = AzulCentroSuave,
    onPrimary = AzulCentroOscuro,
    primaryContainer = Color(0xFF00497F),
    onPrimaryContainer = AzulCentroClaro,
    inversePrimary = AzulCentro,
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
    background = Color(0xFF0F1318),
    onBackground = Color(0xFFE2E2E6),
    surface = Color(0xFF161B21),
    onSurface = Color(0xFFE2E2E6),
    surfaceVariant = Color(0xFF41484D),
    onSurfaceVariant = Color(0xFFC1C7CE),
    surfaceTint = AzulCentroSuave,
    inverseSurface = Color(0xFFE2E2E6),
    inverseOnSurface = Color(0xFF2F3033),
    outline = Color(0xFF8B9198),
    outlineVariant = Color(0xFF3B4652),
    scrim = Color.Black,
    surfaceBright = Color(0xFF343A42),
    surfaceDim = Color(0xFF0F1318),
    surfaceContainerLowest = Color(0xFF0B0F13),
    surfaceContainerLow = Color(0xFF1A2027),
    surfaceContainer = Color(0xFF1F262E),
    surfaceContainerHigh = Color(0xFF242C35),
    surfaceContainerHighest = Color(0xFF2A333D)
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
