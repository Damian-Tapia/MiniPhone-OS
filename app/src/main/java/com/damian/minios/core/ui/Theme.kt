package com.damian.minios.core.ui

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = MiniPrimaryDark,
    onPrimary = MiniOnPrimaryDark,
    primaryContainer = MiniPrimaryContainerDark,
    onPrimaryContainer = MiniOnPrimaryContainerDark,
    secondary = MiniSecondaryDark,
    onSecondary = MiniOnSecondaryDark,
    secondaryContainer = MiniSecondaryContainerDark,
    onSecondaryContainer = MiniOnSecondaryContainerDark,
    tertiary = MiniTertiaryDark,
    onTertiary = MiniOnTertiaryDark,
    tertiaryContainer = MiniTertiaryContainerDark,
    onTertiaryContainer = MiniOnTertiaryContainerDark,
    error = MiniErrorDark,
    onError = MiniOnErrorDark,
    errorContainer = MiniErrorContainerDark,
    onErrorContainer = MiniOnErrorContainerDark,
    background = MiniBackgroundDark,
    onBackground = MiniOnBackgroundDark,
    surface = MiniSurfaceDark,
    onSurface = MiniOnSurfaceDark,
)

private val LightColorScheme = lightColorScheme(
    primary = MiniPrimaryLight,
    onPrimary = MiniOnPrimaryLight,
    primaryContainer = MiniPrimaryContainerLight,
    onPrimaryContainer = MiniOnPrimaryContainerLight,
    secondary = MiniSecondaryLight,
    onSecondary = MiniOnSecondaryLight,
    secondaryContainer = MiniSecondaryContainerLight,
    onSecondaryContainer = MiniOnSecondaryContainerLight,
    tertiary = MiniTertiaryLight,
    onTertiary = MiniOnTertiaryLight,
    tertiaryContainer = MiniTertiaryContainerLight,
    onTertiaryContainer = MiniOnTertiaryContainerLight,
    error = MiniErrorLight,
    onError = MiniOnErrorLight,
    errorContainer = MiniErrorContainerLight,
    onErrorContainer = MiniOnErrorContainerLight,
    background = MiniBackgroundLight,
    onBackground = MiniOnBackgroundLight,
    surface = MiniSurfaceLight,
    onSurface = MiniOnSurfaceLight,
)

/**
 * MiniOSTheme
 * 
 * The main theme wrapper for the application.
 * - Supports Material 3.
 * - Includes Dynamic Color support for Android 12+.
 * - Optimized for a small display form factor.
 */
@Composable
fun MiniOSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
