@file:OptIn(ExperimentalMaterial3Api::class)

package com.comsuon.wp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val DarkColorPalette = darkColorScheme(
    primary = Primary,
    primaryContainer = P_Dark,
    secondary = Secondary,
    surface = P_Light,
    onSecondary = Color.White
)

private val LightColorPalette = lightColorScheme(
    primary = P_Light,
    primaryContainer = Primary,
    secondary = S_Light,
    surface = Surface_Light,
    onSecondary = Color.Black

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun WorkoutPlannerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

val tfTextStyle by lazy {
    TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        fontFamily = FontFamily.SansSerif,
    )
}

@Composable
fun tfColors(
    textColor: Color = Color.White,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    focusedIndicatorColor: Color = Color.Transparent,
    unfocusedIndicatorColor: Color = Color.Transparent,
    disabledIndicatorColor: Color = Color.Transparent
) = TextFieldDefaults.textFieldColors(
    textColor = textColor,
    containerColor = backgroundColor,
    focusedIndicatorColor = focusedIndicatorColor,
    unfocusedIndicatorColor = unfocusedIndicatorColor,
    disabledIndicatorColor = disabledIndicatorColor
)