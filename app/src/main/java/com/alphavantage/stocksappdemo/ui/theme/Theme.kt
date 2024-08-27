package com.alphavantage.stocksappdemo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme

import com.plcoding.stockmarketapp.ui.theme.Shapes

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.plcoding.stockmarketapp.ui.theme.DarkBlue
import com.plcoding.stockmarketapp.ui.theme.TextWhite

private val DarkColorPalette = darkColorScheme(
    primary = Color.Green,
    background = DarkBlue,
    onPrimary = Color.DarkGray,
    onBackground = TextWhite
)

@Composable
fun StockMarketAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}