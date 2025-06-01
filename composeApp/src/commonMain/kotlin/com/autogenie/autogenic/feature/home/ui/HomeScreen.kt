package com.autogenie.autogenic.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.autogenie.autogenic.AppContainer
import com.autogenie.autogenic.feature.home.HomeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onExerciseClick: (String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            GetStartedBanner(
                title = "Get Started",
                onClick = {
                    // Navigate to Learn screen
                }
            )

            SectionTitle(title = "Exercises")

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(uiState.trainings) { training ->
                    Exercise(
                        title = training.name,
                        color = training.color.toColor(),
                        onClick = { onExerciseClick(training.id) }
                    )
                }
            }
        }
    }
}

fun String.toColor(): Color {
    val hex = this.removePrefix("#")
    val colorLong = when (hex.length) {
        6 -> "FF$hex".toLong(16)
        8 -> hex.toLong(16)
        else -> throw IllegalArgumentException("Invalid color format: $this")
    }
    return Color(colorLong)
}

@Composable
fun Exercise(title: String, color: Color, onClick: () -> Unit) {
    val gradient = Brush.linearGradient(
        colors = listOf(
            color.copy(alpha = 1.0f),
            color.copy(alpha = 0.3f)
        )
    )

    Card(
        modifier = Modifier
            .width(140.dp)
            .height(120.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(gradient),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = title)
        }
    }
}

@Composable
fun GetStartedBanner(title: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF2196F3), Color(0xFF9C27B0))
                ),
                shape = MaterialTheme.shapes.large
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Book,
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(viewModel = HomeViewModel(AppContainer.trainingsRepository), onExerciseClick = {})
}

@Preview
@Composable
fun ExercisePreview() {
    Exercise(title = "Push-ups", color = Color.Red, onClick = {})
}

@Preview
@Composable
fun SectionTitlePreview() {
    SectionTitle(title = "Exercises")
}
