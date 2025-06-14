package com.example.turaestates.ui.theme

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    // Define explicit background/surface for components on the gradient
    background = Color(0xFF1C1B1F), // Example: A very dark gray
    surface = Color(0xFF2B2930),   // Example: A slightly lighter dark gray for cards, sheets
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color(0xFFE6E1E5), // Light color for text on dark background
    onSurface = Color(0xFFE6E1E5),   // Light color for text on dark surface
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0)
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    // Define explicit background/surface for components on the gradient
    background = Color(0xFFFFFBFE), // Example: Off-white
    surface = Color(0xFFFDF8FD),   // Example: Slightly different off-white for cards, sheets
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F), // Dark color for text on light background
    onSurface = Color(0xFF1C1B1F),   // Dark color for text on light surface
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F)
)

// Define your gradients
val lightGradientBrush = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFDFCFB), Color(0xFFE2D1C3))
)

val darkGradientBrush = Brush.horizontalGradient(
    colors = listOf(Color(0xFF434343),  Color(0xFF000000)) // Adjusted dark gradient
)

@Composable
fun TuraEstatesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, // Dynamic color is available on Android 12+
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val backgroundBrush = if (darkTheme) darkGradientBrush else lightGradientBrush

    // Handle system bars for edge-to-edge display
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? android.app.Activity)?.window
            window?.let {
                // Allow content to draw behind system bars
                WindowCompat.setDecorFitsSystemWindows(it, false)

                // Make status bar transparent to show gradient behind it
                // Note: For older APIs, you might need different approaches or it might not be fully transparent.
                // This is generally handled well with setDecorFitsSystemWindows(false).
                // it.statusBarColor = Color.Transparent.toArgb() // Often not needed with setDecorFitsSystemWindows

                // Set status bar icon colors (light or dark)
                WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = !darkTheme
                // Set navigation bar icon colors (light or dark)
                WindowCompat.getInsetsController(it, view).isAppearanceLightNavigationBars = !darkTheme
                // Optionally, make navigation bar transparent or translucent
//                 it.navigationBarColor = Color.Transparent.toArgb() // For full gradient
                // Or a semi-transparent color from your scheme:
                 it.navigationBarColor = colorScheme.surface.copy(alpha = 0.3f).toArgb()
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Ensure Typography is defined and passed
        // shapes = Shapes, // Ensure Shapes are defined if you use custom shapes
    ) {
        // Apply the gradient to a Box that wraps all content.
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = Color.Transparent
        ) {
            innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = backgroundBrush)
                    .padding(innerPadding)
            ) {
                content()
            }
        }
    }
}