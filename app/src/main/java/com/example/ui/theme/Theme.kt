package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ThemeColorScheme = lightColorScheme(
    primary = ElectricBlue,
    onPrimary = Color.White,
    primaryContainer = ElectricBlueLight,
    onPrimaryContainer = ElectricBlueDark,
    secondary = GrowthGreen,
    onSecondary = Color.White,
    secondaryContainer = GrowthGreenLight,
    onSecondaryContainer = GrowthGreen,
    tertiary = CautionAmber,
    error = WarningRed,
    onError = Color.White,
    errorContainer = WarningRedLight,
    onErrorContainer = WarningRed,
    background = WarmPaperBackground,
    onBackground = InkBlack,
    surface = WarmPaperSurface,
    onSurface = InkBlack,
    surfaceVariant = WarmPaperSurfaceVariant,
    onSurfaceVariant = InkSecondary,
    outline = BorderSubtleColor,
    outlineVariant = BorderSubtleColor.copy(alpha = 0.6f)
)

private val DarkThemeColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF00388F),
    onPrimaryContainer = Color(0xFFD4E3FF),
    secondary = Color(0xFF34D399),
    onSecondary = Color.Black,
    tertiary = Color(0xFFFBBF24),
    error = Color(0xFFF87171),
    background = Color(0xFF14120E),
    onBackground = Color(0xFFFBF7EE),
    surface = Color(0xFF1E1B15),
    onSurface = Color(0xFFFBF7EE),
    surfaceVariant = Color(0xFF2B2720),
    onSurfaceVariant = Color(0xFFD3CABE),
    outline = Color(0xFF453F34)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Preserve user's requested grid & color theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkThemeColorScheme else ThemeColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
