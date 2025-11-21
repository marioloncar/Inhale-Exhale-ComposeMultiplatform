package com.autogenie.autogenic.feature.exercise.ui

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.autogenie.autogenic.data.trainings.domain.model.StepType
import com.autogenie.autogenic.feature.exercise.ExerciseViewModel
import com.autogenie.autogenic.feature.home.ui.toColor
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(
    exerciseId: String,
    viewModel: ExerciseViewModel,
    onBackClick: () -> Unit
) {
    LaunchedEffect(exerciseId) {
        viewModel.loadExercise(exerciseId)
    }

    val trainingWithColor by viewModel.training.collectAsState()
    val training = trainingWithColor?.training ?: run {
        LoadingScreen()
        return
    }

    val baseColor = trainingWithColor!!.color.toColor()

    var currentStepIndex by remember { mutableIntStateOf(0) }
    var currentCycle by remember { mutableIntStateOf(1) }
    var isRunning by remember { mutableStateOf(true) }

    val currentStep = training.steps[currentStepIndex]

    // Main orb scale
    val scale = remember { Animatable(1f) }

    // subtle secondary pulse
    val subtlePulse = rememberInfiniteTransition()
    val pulse by subtlePulse.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Floating particles
    val particles = remember {
        List(20) {
            Particle(
                offset = Offset(Random.nextFloat(), Random.nextFloat()),
                size = Random.nextFloat() * 6 + 2,
                speed = Random.nextFloat() * 0.002f + 0.0005f
            )
        }
    }

    LaunchedEffect(currentStepIndex, currentCycle) {
        if (!isRunning) return@LaunchedEffect

        when (currentStep.type) {
            StepType.INHALE -> scale.animateTo(
                targetValue = 1.35f,
                animationSpec = tween(
                    durationMillis = currentStep.duration * 1000,
                    easing = FastOutSlowInEasing
                )
            )
            StepType.EXHALE -> scale.animateTo(
                targetValue = 0.65f,
                animationSpec = tween(
                    durationMillis = currentStep.duration * 1000,
                    easing = FastOutSlowInEasing
                )
            )
            StepType.HOLD -> delay(currentStep.duration * 1000L)
        }

        val lastStep = currentStepIndex == training.steps.lastIndex
        if (lastStep) {
            if (currentCycle == training.cycles) isRunning = false
            else {
                currentCycle++
                currentStepIndex = 0
            }
        } else currentStepIndex++
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(training.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
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

            // Pulsating radial background
            PulsatingRadialBackground(baseColor = baseColor, scaleFactor = scale.value)

            // Floating particles layer
            FloatingParticles(particles = particles, baseColor = baseColor)

            // Main breathing orb
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .scale(scale.value * pulse)
                    .background(baseColor.copy(alpha = 0.45f), CircleShape)
                    .blur(8.dp)
            )

            // Labels & cycle info
            Column(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stepLabel(currentStep.type), fontSize = 30.sp, color = Color.White)
                Text(
                    text = "Cycle $currentCycle / ${training.cycles}",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
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
    val animatedOffsets = particles.map { particle ->
        infiniteTransition.animateFloat(
            initialValue = particle.offset.y,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = (2000 / particle.speed).toInt(),
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        particles.forEachIndexed { index, particle ->
            val y = animatedOffsets[index].value * size.height
            val x = particle.offset.x * size.width
            drawCircle(
                color = baseColor.copy(alpha = 0.4f),
                radius = particle.size,
                center = Offset(centerX + x - size.width / 2, centerY + y - size.height / 2)
            )
        }
    }
}

data class Particle(
    val offset: Offset,
    val size: Float,
    val speed: Float
)

@Composable
private fun LoadingScreen() {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Text("Loading...", color = Color.Gray)
    }
}

private fun stepLabel(type: StepType): String = when (type) {
    StepType.INHALE -> "Inhale"
    StepType.EXHALE -> "Exhale"
    StepType.HOLD -> "Hold"
}
