package com.correa.murocampus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Paleta propia, sin color dinamico: la identidad del campus se mantiene igual
// en todos los dispositivos y en ambos modos.
private val EsquemaClaro = lightColorScheme(
    primary = VerdeCampus,
    onPrimary = Color.White,
    primaryContainer = VerdeCampusClaro,
    onPrimaryContainer = VerdeCampusOscuro,
    inversePrimary = VerdeMenta,
    secondary = Salvia,
    onSecondary = Color.White,
    secondaryContainer = SalviaClaro,
    onSecondaryContainer = SalviaOscuro,
    tertiary = Tierra,
    onTertiary = Color.White,
    tertiaryContainer = TierraClaro,
    onTertiaryContainer = TierraOscuro,
    background = FondoClaro,
    onBackground = TextoClaro,
    surface = SuperficieClara,
    onSurface = TextoClaro,
    surfaceVariant = SuperficieVarianteClara,
    onSurfaceVariant = TextoSecundarioClaro,
    surfaceTint = VerdeCampus,
    surfaceDim = SuperficieVarianteClara,
    surfaceBright = SuperficieClara,
    surfaceContainerLowest = SuperficieClara,
    surfaceContainerLow = SuperficieClaraBaja,
    surfaceContainer = SuperficieClaraMedia,
    surfaceContainerHigh = SuperficieClaraAlta,
    surfaceContainerHighest = SuperficieClaraMaxima,
    inverseSurface = VerdeCampusOscuro,
    inverseOnSurface = FondoClaro,
    error = ErrorClaro,
    onError = Color.White,
    errorContainer = ErrorContenedorClaro,
    onErrorContainer = ErrorTextoClaro,
    outline = BordeClaro,
    outlineVariant = BordeSuaveClaro,
    scrim = Color.Black
)

private val EsquemaOscuro = darkColorScheme(
    primary = VerdeMenta,
    onPrimary = VerdeMentaOscuro,
    primaryContainer = VerdeMentaOscuro,
    onPrimaryContainer = VerdeMentaClaro,
    inversePrimary = VerdeCampus,
    secondary = SalviaOscura,
    onSecondary = SalviaTextoSecundario,
    secondaryContainer = SalviaContenedorOscuro,
    onSecondaryContainer = SalviaTextoContenedor,
    tertiary = TierraOscura,
    onTertiary = TierraOscuro,
    tertiaryContainer = TierraContenedorOscuro,
    onTertiaryContainer = TierraTextoContenedor,
    background = FondoOscuro,
    onBackground = TextoOscuro,
    surface = SuperficieOscura,
    onSurface = TextoOscuro,
    surfaceVariant = SuperficieVarianteOscura,
    onSurfaceVariant = TextoSecundarioOscuro,
    surfaceTint = VerdeMenta,
    surfaceDim = FondoOscuro,
    surfaceBright = SuperficieOscuraBrillante,
    surfaceContainerLowest = FondoOscuro,
    surfaceContainerLow = SuperficieOscuraBaja,
    surfaceContainer = SuperficieOscuraMedia,
    surfaceContainerHigh = SuperficieOscuraAlta,
    surfaceContainerHighest = SuperficieOscuraMaxima,
    inverseSurface = TextoOscuro,
    inverseOnSurface = FondoOscuro,
    error = ErrorOscuro,
    onError = ErrorSobreOscuro,
    errorContainer = ErrorContenedorOscuro,
    onErrorContainer = ErrorTextoOscuro,
    outline = BordeOscuro,
    outlineVariant = BordeSuaveOscuro,
    scrim = Color.Black
)

private val Formas = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(14.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun MurocampusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) EsquemaOscuro else EsquemaClaro,
        typography = Typography,
        shapes = Formas,
        content = content
    )
}
