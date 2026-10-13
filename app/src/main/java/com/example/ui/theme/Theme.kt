package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MaritimeLightColorScheme = lightColorScheme(
  primary = OceanNavy,
  onPrimary = Color.White,
  primaryContainer = OceanBlue,
  onPrimaryContainer = Color.White,
  secondary = HeritageGold,
  onSecondary = OceanDeep,
  secondaryContainer = GoldenAmber,
  onSecondaryContainer = OceanDeep,
  tertiary = Terracotta,
  onTertiary = Color.White,
  background = ParchmentCream,
  onBackground = DarkText,
  surface = ParchmentCard,
  onSurface = DarkText,
  surfaceVariant = WarmSand,
  onSurfaceVariant = SubtitleText,
  outline = BorderColor
)

private val MaritimeDarkColorScheme = darkColorScheme(
  primary = OceanCyan,
  onPrimary = OceanDeep,
  primaryContainer = OceanNavy,
  onPrimaryContainer = Color.White,
  secondary = GoldenSun,
  onSecondary = OceanDeep,
  secondaryContainer = HeritageGold,
  onSecondaryContainer = OceanDeep,
  tertiary = GoldenAmber,
  onTertiary = OceanDeep,
  background = OceanDeep,
  onBackground = ParchmentCream,
  surface = Color(0xFF112240),
  onSurface = ParchmentCream,
  surfaceVariant = Color(0xFF233554),
  onSurfaceVariant = WarmSand,
  outline = Color(0xFF3B5274)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep consistent historical maritime branding
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) MaritimeDarkColorScheme else MaritimeLightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
