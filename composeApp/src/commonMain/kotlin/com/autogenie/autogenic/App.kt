package com.autogenie.autogenic

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.autogenie.autogenic.core.ui.AppTheme
import com.autogenie.autogenic.feature.exercise.ui.ExerciseScreen
import com.autogenie.autogenic.feature.home.ui.HomeScreen
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

    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onExerciseClick = { id ->
                navController.navigate("exercise/$id")
            })
        }
        composable("exercise/{id}") { backStackEntry ->
            ExerciseScreen("")
        }
    }
}
