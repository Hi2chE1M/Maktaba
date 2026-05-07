package com.ElOuedUniv.maktaba.presentation.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SlateSecondary,
    onPrimary = WarmBackground,
    secondary = SandTertiary,
    onSecondary = WarmBackground,
    tertiary = SlatePrimary,
    onTertiary = WarmBackground,
    background = Color(0xFF171816),
    onBackground = Color(0xFFF2F0EB),
    surface = Color(0xFF1E201D),
    onSurface = Color(0xFFF2F0EB),
    surfaceVariant = Color(0xFF2C2F2B),
    onSurfaceVariant = Color(0xFFCAC5BC),
    outline = Color(0xFF4E544F),
    outlineVariant = Color(0xFF373A37),
    error = SoftError
)

private val LightColorScheme = lightColorScheme(
    primary = SlatePrimary,
    onPrimary = PureSurface,
    primaryContainer = SoftPrimaryContainer,
    onPrimaryContainer = InkText,
    secondary = SlateSecondary,
    onSecondary = PureSurface,
    secondaryContainer = Color(0xFFEAF0F1),
    onSecondaryContainer = InkText,
    tertiary = SandTertiary,
    onTertiary = PureSurface,
    background = WarmBackground,
    onBackground = InkText,
    surface = PureSurface,
    onSurface = InkText,
    surfaceVariant = SoftSurface,
    onSurfaceVariant = MutedText,
    outline = SoftBorder,
    outlineVariant = SoftBorderVariant,
    error = SoftError
)

@Composable
fun MaktabaTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
