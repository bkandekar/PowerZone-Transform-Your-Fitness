package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// 1. Flame & Gold (Default High-Energy Dark)
private val FlameGoldColorScheme = darkColorScheme(
    primary = FlameOrange,
    onPrimary = Color.White,
    primaryContainer = FlameOrangeDark,
    onPrimaryContainer = Color.White,
    secondary = PowerGold,
    onSecondary = TextDark,
    secondaryContainer = Color(0xFF3B2E15),
    onSecondaryContainer = PowerGoldLight,
    tertiary = NeonLime,
    onTertiary = TextDark,
    background = DarkBackground,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = DarkCardBorder
)

// 2. Cyber Neon (High Intensity Performance)
private val CyberNeonColorScheme = darkColorScheme(
    primary = CyberGreen,
    onPrimary = TextDark,
    primaryContainer = Color(0xFF00B0FF),
    onPrimaryContainer = Color.White,
    secondary = CyberCyan,
    onSecondary = TextDark,
    secondaryContainer = Color(0xFF003840),
    onSecondaryContainer = CyberCyan,
    tertiary = NeonLime,
    onTertiary = TextDark,
    background = CyberDark,
    onBackground = TextPrimary,
    surface = Color(0xFF132326),
    onSurface = TextPrimary,
    surfaceVariant = Color(0xFF1B3035),
    onSurfaceVariant = TextSecondary,
    outline = Color(0xFF2A484F)
)

// 3. Electric Cobalt (Power & Recovery)
private val ElectricCobaltColorScheme = darkColorScheme(
    primary = CobaltBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF1A237E),
    onPrimaryContainer = Color.White,
    secondary = ElectricPurple,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF4A148C),
    onSecondaryContainer = ElectricPurple,
    tertiary = CyberCyan,
    onTertiary = TextDark,
    background = CobaltDark,
    onBackground = TextPrimary,
    surface = Color(0xFF101C3D),
    onSurface = TextPrimary,
    surfaceVariant = Color(0xFF182854),
    onSurfaceVariant = TextSecondary,
    outline = Color(0xFF283F7E)
)

// 4. Crimson VIP (Luxury Elite Club)
private val CrimsonVipColorScheme = darkColorScheme(
    primary = CrimsonRed,
    onPrimary = Color.White,
    primaryContainer = CrimsonDark,
    onPrimaryContainer = Color.White,
    secondary = MetallicGold,
    onSecondary = TextDark,
    secondaryContainer = Color(0xFF423200),
    onSecondaryContainer = MetallicGold,
    tertiary = PowerGold,
    onTertiary = TextDark,
    background = VipDark,
    onBackground = TextPrimary,
    surface = Color(0xFF1F0D16),
    onSurface = TextPrimary,
    surfaceVariant = Color(0xFF2D1420),
    onSurfaceVariant = TextSecondary,
    outline = Color(0xFF4A2234)
)

@Composable
fun PowerZoneTheme(
    appTheme: String = "FlameGold",
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when (appTheme) {
        "CyberNeon" -> CyberNeonColorScheme
        "ElectricCobalt" -> ElectricCobaltColorScheme
        "CrimsonVIP" -> CrimsonVipColorScheme
        else -> FlameGoldColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    PowerZoneTheme(appTheme = "FlameGold", darkTheme = darkTheme, content = content)
}

