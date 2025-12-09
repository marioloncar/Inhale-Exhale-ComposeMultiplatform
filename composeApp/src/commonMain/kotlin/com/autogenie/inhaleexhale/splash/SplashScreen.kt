package com.autogenie.inhaleexhale.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var fadeOut by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (fadeOut) 0f else 1f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        finishedListener = {
            if (fadeOut) onFinished()
        }
    )

    val infinite = rememberInfiniteTransition()

    val pulse by infinite.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            tween(2400, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    val rotation by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(6000, easing = LinearEasing),
            RepeatMode.Restart
        )
    )

    val colorShift by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(3000, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    val primary = MaterialTheme.colorScheme.primary
    val dynamicColor = lerp(primary, primary.copy(alpha = 0.5f), colorShift)

    LaunchedEffect(Unit) {
        delay(1400) // splash duration
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

            repeat(18) { i ->
                val angle = (i * 20 + rotation) * (3.14f / 180f)
                val radius = (i % 5 + 1) * 60f * pulse
                val x = center.x + cos(angle) * radius
                val y = center.y + sin(angle) * radius

                drawCircle(
                    color = dynamicColor.copy(alpha = 0.12f),
                    radius = (i % 3 + 1) * 4f,
                    center = Offset(x, y)
                )
            }

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(dynamicColor.copy(alpha = 0.25f), Color.Transparent)
                ),
                radius = 380f * pulse,
                center = center
            )

            rotate(rotation, center) {
                drawCircle(
                    brush = Brush.sweepGradient(
                        listOf(
                            dynamicColor.copy(alpha = 0.2f),
                            dynamicColor.copy(alpha = 0.05f),
                            Color.Transparent,
                            dynamicColor.copy(alpha = 0.15f)
                        )
                    ),
                    radius = 260f * pulse,
                    center = center
                )
            }

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        dynamicColor.copy(alpha = 0.45f),
                        dynamicColor.copy(alpha = 0.1f),
                        Color.Transparent
                    )
                ),
                radius = 180f * pulse,
                center = center
            )
        }
    }
}