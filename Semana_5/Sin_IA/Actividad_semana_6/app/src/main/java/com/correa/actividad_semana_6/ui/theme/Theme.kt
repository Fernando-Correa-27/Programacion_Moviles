package com.correa.actividad_semana_6.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ClinicaLightColorScheme = lightColorScheme(
    primary = MoradoPrincipal,
    onPrimary = Blanco,
    primaryContainer = LilaClaro,
    onPrimaryContainer = MoradoOscuro,
    secondary = VerdeExito,
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
fun Actividad_semana_6Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ClinicaLightColorScheme,
        typography = Typography,
        content = content
    )
}