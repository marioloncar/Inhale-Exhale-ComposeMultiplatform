package com.autogenie.autogenic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        AppNavigation()
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val homeViewModel = remember {
        HomeViewModel(observeTrainingsUseCase = AppContainer.observeTrainingsUseCase)
    }

    val exerciseViewModel = remember {
        ExerciseViewModel(observeTrainingsUseCase = AppContainer.observeTrainingsUseCase)
    }

    val settingsViewModel = remember {
        SettingsViewModel(preferencesRepository = AppContainer.preferencesRepository)
    }

    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = homeViewModel,
                onExerciseClick = { id -> navController.navigate("exercise/$id") },
                onSettingsClick = { navController.navigate("settings") },
                onGetStartedClick = { navController.navigate("tutorial") },
            )
        }

        composable("exercise/{id}") { backStackEntry ->
            val id = backStackEntry.savedStateHandle.get<String>("id")
                ?: return@composable
            ExerciseScreen(
                exerciseId = id,
                viewModel = exerciseViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = "settings") { backstackEntry ->
            SettingsScreen(
                viewModel = settingsViewModel,
                onBackClick = { navController.popBackStack() })
        }

        composable("tutorial") { backstackEntry ->
            TutorialScreen(onBackClick = { navController.popBackStack() })
        }
    }
}
