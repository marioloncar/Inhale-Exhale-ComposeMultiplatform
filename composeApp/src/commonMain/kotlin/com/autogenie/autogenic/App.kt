package com.autogenie.autogenic

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.lerp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.autogenie.autogenic.core.ui.AppTheme
import com.autogenie.autogenic.feature.exercise.ExerciseViewModel
import com.autogenie.autogenic.feature.exercise.ui.ExerciseScreen
import com.autogenie.autogenic.feature.home.HomeViewModel
import com.autogenie.autogenic.feature.home.ui.HomeScreen
import com.autogenie.autogenic.feature.settings.SettingsViewModel
import com.autogenie.autogenic.feature.settings.ui.SettingsScreen
import com.autogenie.autogenic.feature.tutorial.ui.TutorialScreen
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.cos
import kotlin.math.sin

@Composable
@Preview
fun App() {
    var showSplash by remember { mutableStateOf(true) }

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Crossfade(targetState = showSplash, animationSpec = tween(500)) { isSplash ->
                if (isSplash) {
                    SplashScreen(onFinished = { showSplash = false })
                } else {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {

        composable("home") {
            val viewModel = remember {
                HomeViewModel(
                    trainingsRepository = AppContainer.trainingsRepository,
                    preferencesRepository = AppContainer.preferencesRepository
                )
            }

            HomeScreen(
                viewModel = viewModel,
                onExerciseClick = { id -> navController.navigate("exercise/$id") },
                onSettingsClick = { navController.navigate("settings") },
                onGetStartedClick = { navController.navigate("tutorial") },
            )
        }

        composable("exercise/{id}") { backStackEntry ->
            val id = backStackEntry.savedStateHandle.get<String>("id")
                ?: return@composable

            val viewModel = remember {
                ExerciseViewModel(
                    trainingsRepository = AppContainer.trainingsRepository,
                    preferencesRepository = AppContainer.preferencesRepository
                )
            }

            ExerciseScreen(
                exerciseId = id,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = "settings") { backstackEntry ->
            val settingsViewModel = remember {
                SettingsViewModel(preferencesRepository = AppContainer.preferencesRepository)
            }

            SettingsScreen(
                viewModel = settingsViewModel,
                onBackClick = { navController.popBackStack() })
        }

        composable("tutorial") { backstackEntry ->
            TutorialScreen(onBackClick = { navController.popBackStack() })
        }
    }
}

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var visible by remember { mutableStateOf(true) }

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
            tween(durationMillis = 6000, easing = LinearEasing),
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

    // shorter splash duration
    LaunchedEffect(Unit) {
        delay(1600)
        visible = false
        delay(200)
        onFinished()
    }

    if (!visible) return

    Box(
        modifier = Modifier.fillMaxSize(),
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

