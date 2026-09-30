package com.correa.tecsup_store.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = MoradoTecsupDark,
    onPrimary = Color(0xFF2A1A4A),
    primaryContainer = MoradoTecsupOscuro,
    onPrimaryContainer = Color(0xFFEADDFF),
    secondary = TurquesaTecsupDark,
    onSecondary = Color(0xFF00382F),
    secondaryContainer = Color(0xFF005045),
    onSecondaryContainer = Color(0xFF9CF2E2),
    tertiary = AcentoTecsupDark,
    onTertiary = Color(0xFF4A2800)
)

private val LightColorScheme = lightColorScheme(
    primary = MoradoTecsup,
    onPrimary = Color.White,
    primaryContainer = MoradoTecsupClaro,
    onPrimaryContainer = MoradoTecsupOscuro,
    secondary = TurquesaTecsup,
    onSecondary = Color.White,
    secondaryContainer = TurquesaTecsupClaro,
    onSecondaryContainer = Color(0xFF00382F),
    tertiary = AcentoTecsup,
    onTertiary = Color.White,
    background = FondoTienda,
    onBackground = Color(0xFF1C1B1F),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = VarianteSuperficie,
    onSurfaceVariant = TextoSuave
)

@Composable
fun TECSUP_StoreTheme(
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
