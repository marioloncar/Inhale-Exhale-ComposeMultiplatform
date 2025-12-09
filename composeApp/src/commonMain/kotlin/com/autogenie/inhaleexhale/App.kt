package com.autogenie.inhaleexhale

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.autogenie.inhaleexhale.core.ui.AppTheme
import com.autogenie.inhaleexhale.feature.exercise.ExerciseViewModel
import com.autogenie.inhaleexhale.feature.exercise.ui.ExerciseScreen
import com.autogenie.inhaleexhale.feature.home.HomeViewModel
import com.autogenie.inhaleexhale.feature.home.ui.HomeScreen
import com.autogenie.inhaleexhale.feature.settings.SettingsViewModel
import com.autogenie.inhaleexhale.feature.settings.ui.SettingsScreen
import com.autogenie.inhaleexhale.feature.tutorial.ui.TutorialScreen
import com.autogenie.inhaleexhale.splash.SplashScreen
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
