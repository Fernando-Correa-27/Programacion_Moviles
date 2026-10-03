package com.tecsup.mibodega.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BodegaColorScheme = lightColorScheme(
    primary = VerdeBodega,
    onPrimary = Blanco,
    secondary = AzulEnlace,
    background = Blanco,
    onBackground = AzulTexto,
    surface = Blanco,
    onSurface = AzulTexto,
    surfaceVariant = GrisClaro,
    onSurfaceVariant = GrisTexto,
    outline = GrisBorde,
    error = RojoPrecio
)

private val BodegaDarkColorScheme = darkColorScheme(
    primary = Color(0xFF81C784),
    onPrimary = Color(0xFF102414),
    secondary = Color(0xFF90CAF9),
    background = Color(0xFF121612),
    onBackground = Color(0xFFE6EAE6),
    surface = Color(0xFF1B211D),
    onSurface = Color(0xFFE6EAE6),
    surfaceVariant = Color(0xFF303A33),
    onSurfaceVariant = Color(0xFFBEC9BF),
    outline = Color(0xFF718074),
    error = Color(0xFFFF8A80)
)

@Composable
fun BodegaTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) BodegaDarkColorScheme else BodegaColorScheme,
        typography = BodegaTypography,
        content = content
    )
}
