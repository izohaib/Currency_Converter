package com.example.currency_converter.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

data class CurrencyConverter(
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val isLight: Boolean,
)

val appThemeColors = CurrencyConverter(
    background = Color(0xFF0F0F0F),
    surface = Color(0xFF1F1F1F),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFA0A0A0),
    isLight = false
)

val LocalTheme = staticCompositionLocalOf {
    appThemeColors
}