package com.autogenie.inhaleexhale.feature.exercise.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.autogenie.inhaleexhale.core.util.KeepScreenOn
import com.autogenie.inhaleexhale.core.util.TTS
import com.autogenie.inhaleexhale.core.util.toColor
import com.autogenie.inhaleexhale.data.trainings.domain.model.StepType
import com.autogenie.inhaleexhale.feature.exercise.ExerciseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(
    exerciseId: String,
    viewModel: ExerciseViewModel,
    onBackClick: () -> Unit
) {
    KeepScreenOn()

    LaunchedEffect(exerciseId) { viewModel.loadExercise(exerciseId) }

    val haptics = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    val trainingUiModel by viewModel.training.collectAsState()
    val training = trainingUiModel?.training ?: run {
        LoadingScreen()
        return
    }

    val baseColor = trainingUiModel!!.color.toColor()
    val isInfiniteCycle = trainingUiModel!!.isInfiniteCycle

    var currentStepIndex by remember { mutableStateOf(0) }
    var currentCycle by remember { mutableStateOf(1) }
    var isRunning by remember { mutableStateOf(true) }
    var showInfoDialog by remember { mutableStateOf(false) }
    var startExercise by remember { mutableStateOf(true) }

    val currentStep = training.steps[currentStepIndex]

    var countdown by remember { mutableStateOf(3) }
    var isCountdownFinished by remember { mutableStateOf(false) }

    LaunchedEffect(startExercise) {
        while (countdown > 0) {
            haptics.vibrateShortly()
            TTS.speak(countdown.toString())
            delay(1000)
            countdown--
        }
        haptics.vibrateShortly()
        delay(1000)
        isCountdownFinished = true
    }

    val scale = remember { Animatable(1f) }

    val pulse: Float = if (isRunning) {
        val subtlePulse = rememberInfiniteTransition(label = "SubtlePulseTransition")
        subtlePulse.animateFloat(
            initialValue = 0.95f,
            targetValue = 1.05f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "PulseAnimation"
        ).value
    } else {
        1.0f
    }

    val particles = remember {
        List(20) {
            Particle(
                offset = Offset(Random.nextFloat(), Random.nextFloat()),
                size = Random.nextFloat() * 6 + 2,
                speed = Random.nextFloat() * 0.002f + 0.0005f
            )
        }
    }

    LaunchedEffect(currentStepIndex, currentCycle, isCountdownFinished, isRunning) {
        if (!isCountdownFinished || !isRunning) return@LaunchedEffect

        delay(500)

        when (currentStep.type) {
            StepType.INHALE -> {
                haptics.vibrateFirmly()
                TTS.speak("Inhale")
                scale.animateTo(
                    targetValue = 1.35f,
                    animationSpec = tween(
                        durationMillis = currentStep.duration * 1000,
                        easing = FastOutSlowInEasing
                    )
                )
            }
            StepType.EXHALE -> {
                haptics.vibrateFirmly()
                TTS.speak("Exhale")
                scale.animateTo(
                    targetValue = 0.65f,
                    animationSpec = tween(
                        durationMillis = currentStep.duration * 1000,
                        easing = FastOutSlowInEasing
                    )
                )
            }
            StepType.HOLD -> {
                haptics.vibrateFirmly()
                TTS.speak("Hold")
                delay(currentStep.duration * 1000L)
            }
        }

        val lastStep = currentStepIndex == training.steps.lastIndex

        if (lastStep) {
            if (currentCycle == training.cycles) {
                if (isInfiniteCycle) {
                    currentCycle = 1
                    currentStepIndex = 0
                } else {
                    isRunning = false
                    scale.snapTo(1f)
                }
            } else {
                currentCycle++
                currentStepIndex = 0
            }
        } else {
            currentStepIndex++
        }
    }

    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = { Text(training.name, color = MaterialTheme.colorScheme.onBackground) },
            text = { Text(training.description, color = MaterialTheme.colorScheme.onBackground) },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = false }) {
                    Text("Close", color = MaterialTheme.colorScheme.primary)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(training.name, color = MaterialTheme.colorScheme.onBackground) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onBackground)
                    }
                },
                actions = {
                    IconButton(onClick = { showInfoDialog = true }) {
                        Icon(Icons.Default.Info, contentDescription = "Info", tint = MaterialTheme.colorScheme.onBackground)
                    }
                }
            )
        }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {

            PulsatingRadialBackground(baseColor = baseColor, scaleFactor = scale.value)
            FloatingParticles(particles = particles, baseColor = baseColor)

            Box(
                modifier = Modifier
                    .size(260.dp)
                    .scale(scale.value * pulse)
                    .background(baseColor.copy(alpha = 0.45f), CircleShape)
                    .blur(8.dp)
            )

            Column(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isRunning) {
                    Text(stepLabel(currentStep.type), fontSize = 30.sp, color = MaterialTheme.colorScheme.onBackground)
                    Text(
                        text = "Cycle $currentCycle / ${training.cycles}" + if (isInfiniteCycle) " (∞)" else "",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                } else {
                    TTS.speak("Exercise complete")
                    Text("Exercise Complete!", fontSize = 28.sp, color = MaterialTheme.colorScheme.onBackground)
                    TextButton(onClick = {
                        scope.launch {
                            scale.snapTo(1f)
                        }

                        currentStepIndex = 0
                        currentCycle = 1
                        isRunning = true
                        countdown = 3
                        isCountdownFinished = false
                        startExercise = !startExercise
                    }) {
                        Text("Restart Exercise", fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            if (!isCountdownFinished) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.45f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (countdown > 0) countdown.toString() else "Start",
                        fontSize = 64.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

@Composable
fun PulsatingRadialBackground(baseColor: Color, scaleFactor: Float) {
    val infiniteTransition = rememberInfiniteTransition()
    val radius by infiniteTransition.animateFloat(
        initialValue = 150f,
        targetValue = 450f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    baseColor.copy(alpha = alpha),
                    baseColor.copy(alpha = alpha + 0.15f),
                    Color.Transparent
                ),
                center = Offset(size.width / 2, size.height / 2),
                radius = radius * scaleFactor,
                tileMode = TileMode.Clamp
            ),
            radius = radius * scaleFactor,
            center = Offset(size.width / 2, size.height / 2)
        )
    }
}

@Composable
fun FloatingParticles(particles: List<Particle>, baseColor: Color) {
    val infiniteTransition = rememberInfiniteTransition()

    val animatedParticles = particles.map { particle ->
        val animatedY = infiniteTransition.animateFloat(
            initialValue = particle.offset.y,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween((4000 / particle.speed).toInt(), easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        val animatedX = infiniteTransition.animateFloat(
            initialValue = particle.offset.x,
            targetValue = particle.offset.x + Random.nextFloat() * 0.1f - 0.05f,
            animationSpec = infiniteRepeatable(
                animation = tween((5000 / particle.speed).toInt(), easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        val animatedAlpha = infiniteTransition.animateFloat(
            initialValue = 0.2f,
            targetValue = 0.7f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
        val animatedSize = infiniteTransition.animateFloat(
            initialValue = particle.size,
            targetValue = particle.size * (0.5f + Random.nextFloat()),
            animationSpec = infiniteRepeatable(
                animation = tween(2500, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Triple(animatedX, animatedY, Pair(animatedSize, animatedAlpha))
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        animatedParticles.forEach { triple ->
            val (xAnim, yAnim, sizeAlpha) = triple
            val (radiusAnim, alphaAnim) = sizeAlpha
            val x = xAnim.value * size.width
            val y = yAnim.value * size.height
            val radius = radiusAnim.value
            val alpha = alphaAnim.value

            drawCircle(
                color = baseColor.copy(alpha = alpha),
                radius = radius,
                center = Offset(centerX + x - size.width / 2, centerY + y - size.height / 2)
            )
        }
    }
}

data class Particle(val offset: Offset, val size: Float, val speed: Float)

private fun HapticFeedback.vibrateShortly() { performHapticFeedback(HapticFeedbackType.Confirm) }
private fun HapticFeedback.vibrateFirmly() { performHapticFeedback(HapticFeedbackType.Reject) }

@Composable
private fun LoadingScreen() {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Text("Loading...", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
    }
}

private fun stepLabel(type: StepType): String = when (type) {
    StepType.INHALE -> "Inhale"
    StepType.EXHALE -> "Exhale"
    StepType.HOLD -> "Hold"
}
