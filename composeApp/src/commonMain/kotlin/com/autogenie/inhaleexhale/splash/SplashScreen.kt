package com.autogenie.inhaleexhale.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var fadeOut by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (fadeOut) 0f else 1f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        finishedListener = {
            if (fadeOut) onFinished()
        },
        label = "AlphaFadeOut"
    )

    val infinite = rememberInfiniteTransition(label = "BreathingPulseTransition")

    val pulse by infinite.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = FastOutSlowInEasing), // Breathing duration
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseAnimation"
    )

    val primaryColor = MaterialTheme.colorScheme.primary

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)
        fadeOut = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { this.alpha = alpha },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)

            val baseRadius = 150.dp.toPx()
            val currentRadius = baseRadius * pulse

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(primaryColor.copy(alpha = 0.2f), Color.Transparent)
                ),
                radius = currentRadius * 1.5f,
                center = center
            )

            drawCircle(
                color = primaryColor.copy(alpha = 0.45f),
                radius = currentRadius,
                center = center
            )

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        primaryColor.copy(alpha = 0.8f),
                        primaryColor.copy(alpha = 0.3f),
                        Color.Transparent
                    )
                ),
                radius = currentRadius * 0.4f,
                center = center
            )
        }
    }
}
