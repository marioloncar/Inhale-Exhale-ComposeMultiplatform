package com.autogenie.autogenic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(1200)
        visible = false
        delay(300)
        onFinished()
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 600)),
        exit = fadeOut(animationSpec = tween(durationMillis = 300))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "InhaleExhale",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
        }
    }
}
