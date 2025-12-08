package com.autogenie.autogenic.feature.tutorial.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.isActive
import kotlinx.coroutines.delay

enum class StepType { INHALE, HOLD, EXHALE }
data class BreathingStep(val type: StepType, val duration: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialScreen(onBackClick: () -> Unit) {
    val steps = listOf(
        BreathingStep(StepType.INHALE, 4),
        BreathingStep(StepType.HOLD, 4),
        BreathingStep(StepType.EXHALE, 6)
    )

    var currentStepIndex by remember { mutableStateOf(0) }
    val currentStep = steps[currentStepIndex]

    val scale = remember { Animatable(1f) }

    // Animate breathing orb
    LaunchedEffect(currentStepIndex) {
        while (isActive) {
            when (currentStep.type) {
                StepType.INHALE -> scale.animateTo(
                    1.05f,
                    animationSpec = tween(currentStep.duration * 1000, easing = FastOutSlowInEasing)
                )
                StepType.EXHALE -> scale.animateTo(
                    0.95f,
                    animationSpec = tween(currentStep.duration * 1000, easing = FastOutSlowInEasing)
                )
                StepType.HOLD -> delay(currentStep.duration * 1000L)
            }
            currentStepIndex = (currentStepIndex + 1) % steps.size
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.SelfImprovement, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ===========================
            // Subtle full-screen breathing background
            // ===========================
            val infiniteTransition = rememberInfiniteTransition()
            val bgRadius by infiniteTransition.animateFloat(
                initialValue = 400f,
                targetValue = 600f,
                animationSpec = infiniteRepeatable(
                    animation = tween(6000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            val bgAlpha by infiniteTransition.animateFloat(
                initialValue = 0.1f,
                targetValue = 0.25f,
                animationSpec = infiniteRepeatable(
                    animation = tween(6000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .scale(scale.value)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF80D8FF).copy(alpha = bgAlpha),
                                Color(0xFF039BE5).copy(alpha = bgAlpha + 0.05f),
                                Color.Transparent
                            ),
                            radius = bgRadius
                        ),
                        shape = CircleShape
                    )
                    .blur(50.dp)
            )

            // ===========================
            // Foreground content
            // ===========================
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // Tutorial instructions
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Breathing Basics",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "1. Sit or lie down comfortably in a quiet place.",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "2. Close your eyes gently and relax your body.",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "3. Follow the breathing orb and text cues above.",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "4. Inhale when it says 'Inhale', hold when it says 'Hold', and exhale when it says 'Exhale'.",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "5. Repeat for several minutes, focusing on smooth breathing.",
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        "Why It Helps",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "Deep breathing activates the parasympathetic nervous system, calming your body and reducing stress.",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "You're doing great! ✨",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
