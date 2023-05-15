@file:OptIn(ExperimentalMaterial3Api::class)

package com.comsuon.wp.ui.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkColorPalette = darkColorScheme(
    primary = Primary,
    primaryContainer = P_Dark,
    secondary = Secondary,
    surface = P_Light,
    onSecondary = Color.White,
    onPrimaryContainer = Color.White,
    onSurface = Primary

)

private val LightColorPalette = lightColorScheme(
    primary = P_Light,
    primaryContainer = Primary,
    secondary = S_Light,
    surface = Surface_Light,
    onSecondary = Color.Black,
    onPrimaryContainer = Text_Primary,
    onSurface = P_Light

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
    androidTheme: Boolean = false,
    disableDynamicTheming: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colors = when {
        androidTheme -> if (darkTheme) {
            DarkColorPalette
        } else {
            LightColorPalette
        }
        !disableDynamicTheming && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> if (darkTheme) DarkColorPalette else LightColorPalette
    }

    val backgroundTheme = when {
        androidTheme -> if (darkTheme) BackgroundTheme(color = P_Dark) else BackgroundTheme(color = P_Light)
        else -> BackgroundTheme(
            color = colors.surface,
            tonalElevation = 2.dp
        )
    }

    val tintTheme = when {
        androidTheme -> TintTheme()
        !disableDynamicTheming && supportsDynamicTheming() -> TintTheme(colors.primary)
        else -> TintTheme(colors.primary)
    }

    // Composition locals
    CompositionLocalProvider(
        LocalBackgroundTheme provides backgroundTheme,
        LocalTintTheme provides  tintTheme
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

@Composable
fun tfTextStyle(): TextStyle =
    MaterialTheme.typography.bodyMedium


@OptIn(ExperimentalMaterial3Api::class)
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

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S