package com.autogenie.autogenic.feature.exercise.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun ExerciseScreen(exerciseId: String) {
    val baseColor = Color(0xFF2196F3)
    val instructions = listOf(
        "Breathe in through your nose",
        "Hold your breath for a moment",
        "Exhale slowly through your mouth",
        "Relax your shoulders"
    )

    var animate by remember { mutableStateOf(false) }
    var isInhaling by remember { mutableStateOf(true) }
    var showBreathing by remember { mutableStateOf(true) }
    var showInstructions by remember { mutableStateOf(false) }
    var currentInstructionIndex by remember { mutableStateOf(0) }

    // Breathing cycle
    LaunchedEffect(Unit) {
        repeat(2) { // 12 seconds, toggling every 6s
            delay(6000)
            isInhaling = !isInhaling
        }
        showBreathing = false
        delay(1000)
        showInstructions = true

        while (true) {
            delay(8000)
            currentInstructionIndex = (currentInstructionIndex + 1) % instructions.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PulsatingBrush(animate, baseColor)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            AnimatedContent(
                targetState = showBreathing to isInhaling,
                transitionSpec = {
                    ContentTransform(
                        targetContentEnter = fadeIn(),
                        initialContentExit = fadeOut()
                    )
                },
                label = "Breathing"
            ) { (breathing, inhale) ->
                if (breathing) {
                    Text(
                        text = if (inhale) "Take a deep breath..." else "Exhale slowly...",
                        fontSize = 24.sp,
                        color = Color.White
                    )
                }
            }

            AnimatedContent(
                targetState = showInstructions to currentInstructionIndex,
                transitionSpec = {
                    ContentTransform(
                        targetContentEnter = fadeIn(),
                        initialContentExit = fadeOut()
                    )
                },
                label = "Instruction"
            ) { (show, index) ->
                if (show) {
                    Text(
                        text = instructions[index],
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        animate = true
    }
}

@Composable
private fun PulsatingBrush(animate: Boolean, baseColor: Color): Brush {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val radius by infiniteTransition.animateFloat(
        initialValue = 600f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "radius"
    )

    return Brush.radialGradient(
        colors = listOf(
            baseColor.copy(alpha = 0.4f),
            baseColor,
            Color.Transparent
        ),
        radius = radius,
        tileMode = TileMode.Clamp
    )
}
