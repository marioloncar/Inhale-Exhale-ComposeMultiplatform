package com.autogenie.autogenic

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.material3.Text
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

    val infiniteTransition = rememberInfiniteTransition()
    val radius by infiniteTransition.animateFloat(
        initialValue = 100f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val primaryColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.onBackground

    LaunchedEffect(Unit) {
        delay(2000)
        visible = false
        delay(300)
        onFinished()
    }

    if (visible) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            primaryColor.copy(alpha = 0.3f),
                            Color.Transparent
                        ),
                        center = Offset(size.width / 2, size.height / 2),
                        radius = radius
                    ),
                    radius = radius,
                    center = Offset(size.width / 2, size.height / 2)
                )
            }

            Text(
                text = "Inhale - Exhale",
                color = textColor,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}



