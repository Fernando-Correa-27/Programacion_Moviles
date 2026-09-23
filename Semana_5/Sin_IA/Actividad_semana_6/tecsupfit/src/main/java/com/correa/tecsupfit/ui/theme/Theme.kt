package com.correa.tecsupfit.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val TecsupFitLightColorScheme = lightColorScheme(
    primary = VerdePrincipal,
    onPrimary = Blanco,
    primaryContainer = VerdeMenta,
    onPrimaryContainer = VerdeMentaOscuro,
    secondary = VerdeBoton,
    onSecondary = Blanco,
    secondaryContainer = VerdeMenta,
    onSecondaryContainer = VerdeMentaOscuro,
    background = GrisFondo,
    onBackground = TextoPrincipal,
    surface = Blanco,
    onSurface = TextoPrincipal,
    surfaceVariant = GrisTarjeta,
    onSurfaceVariant = GrisTexto,
    outline = GrisBadge,
    outlineVariant = GrisBadge,
    error = Color(0xFFB3261E)
)

@Composable
fun TecsupFitTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = TecsupFitLightColorScheme,
        typography = Typography,
        content = content
    )
}