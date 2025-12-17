package com.autogenie.inhaleexhale.feature.commonui

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun AmbientBackground(primaryColor: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "AuraFlow")

    // --- ORB 1: The "Slow Wanderer" (Primary Color) ---
    val x1 by infiniteTransition.animateFloat(
        initialValue = 0.1f, targetValue = 0.9f,
        animationSpec = infiniteRepeatable(tween(12000, easing = LinearEasing), RepeatMode.Reverse),
        label = "Orb1_X"
    )
    val y1 by infiniteTransition.animateFloat(
        initialValue = 0.2f, targetValue = 0.8f,
        animationSpec = infiniteRepeatable(tween(18000, easing = LinearEasing), RepeatMode.Reverse),
        label = "Orb1_Y"
    )

    // --- ORB 2: The "Pulse" (Secondary/Lighter Tone) ---
    val x2 by infiniteTransition.animateFloat(
        initialValue = 0.8f, targetValue = 0.2f,
        animationSpec = infiniteRepeatable(tween(15000, easing = LinearEasing), RepeatMode.Reverse),
        label = "Orb2_X"
    )
    val y2 by infiniteTransition.animateFloat(
        initialValue = 0.7f, targetValue = 0.1f,
        animationSpec = infiniteRepeatable(tween(11000, easing = LinearEasing), RepeatMode.Reverse),
        label = "Orb2_Y"
    )

    // Breathing Scale for both
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f, targetValue = 1.6f,
        animationSpec = infiniteRepeatable(tween(8000, easing = EaseInOutSine), RepeatMode.Reverse),
        label = "GlobalPulse"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Draw Orb 1
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(primaryColor.copy(alpha = 0.15f), Color.Transparent),
                center = Offset(w * x1, h * y1),
                radius = w * pulseScale
            ),
            center = Offset(w * x1, h * y1)
        )

        // Draw Orb 2 (Slightly different hue or just shifted alpha)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(primaryColor.copy(alpha = 0.1f), Color.Transparent),
                center = Offset(w * x2, h * y2),
                radius = w * (pulseScale * 1.2f) // Make this one slightly larger
            ),
            center = Offset(w * x2, h * y2)
        )
    }
}