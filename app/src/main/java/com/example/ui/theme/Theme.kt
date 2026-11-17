package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val MupaDarkColorScheme = darkColorScheme(
    primary = MupaBlueLight,
    onPrimary = MupaBlueDark,
    primaryContainer = MupaBlueContainerDark,
    onPrimaryContainer = MupaBlueContainer,
    secondary = MupaOrangeLight,
    onSecondary = Color(0xFF3A1600),
    secondaryContainer = MupaOrangeContainerDark,
    onSecondaryContainer = MupaOrangeContainer,
    tertiary = MupaGoldLight,
    onTertiary = Color(0xFF2B2000),
    tertiaryContainer = MupaGoldContainerDark,
    onTertiaryContainer = MupaGoldContainer,
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkOutline
)

private val MupaLightColorScheme = lightColorScheme(
    primary = MupaBlue,
    onPrimary = Color.White,
    primaryContainer = MupaBlueContainer,
    onPrimaryContainer = MupaOnBlueContainer,
    secondary = MupaOrange,
    onSecondary = Color.White,
    secondaryContainer = MupaOrangeContainer,
    onSecondaryContainer = MupaOnOrangeContainer,
    tertiary = MupaGold,
    onTertiary = MupaOnGoldContainer,
    tertiaryContainer = MupaGoldContainer,
    onTertiaryContainer = MupaOnGoldContainer,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextSecondary,
    outline = LightOutline
)

val MupaShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun MupaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) MupaDarkColorScheme else MupaLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = MupaShapes,
        content = content
    )
}
