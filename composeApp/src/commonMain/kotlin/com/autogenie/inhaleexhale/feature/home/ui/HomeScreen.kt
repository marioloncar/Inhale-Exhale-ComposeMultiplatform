package com.autogenie.inhaleexhale.feature.home.ui

// ... existing imports
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.autogenie.inhaleexhale.AppContainer
import com.autogenie.inhaleexhale.core.util.toColor
import com.autogenie.inhaleexhale.data.trainings.domain.model.Category
import com.autogenie.inhaleexhale.feature.commonui.AmbientBackground
import com.autogenie.inhaleexhale.feature.home.HomeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onExerciseClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onGetStartedClick: () -> Unit,
    onCategoryClick: (Category) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    val primaryColor = uiState.trainings.firstOrNull()?.color?.toColor()
        ?: MaterialTheme.colorScheme.primary

    Box(modifier = Modifier.fillMaxSize()) {
        AmbientBackground(primaryColor = primaryColor)

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                MinimalFloatingTopBar(
                    title = "Inhale - Exhale",
                    onSettingsClick = onSettingsClick
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                FloatingGetStartedAction(primaryColor = primaryColor, onClick = onGetStartedClick)

                MoodGoalSelectionRow(
                    categories = uiState.moodCategories,
                    selectedCategory = uiState.selectedCategory,
                    onCategoryClick = onCategoryClick
                )

                SectionTitle(title = "Breathing Flow", modifier = Modifier.padding(top = 16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollState)
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Spacer(modifier = Modifier.width(24.dp - 16.dp))

                    uiState.trainings.forEach { training ->
                        FlowExerciseCard(
                            title = training.training.name,
                            description = training.training.summary,
                            color = training.color.toColor(),
                            onClick = { onExerciseClick(training.training.id) }
                        )
                    }

                    Spacer(modifier = Modifier.width(24.dp - 16.dp))
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun GoalChip(
    category: Category,
    isSelected: Boolean,
    onClick: (Category) -> Unit
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Row(
        modifier = Modifier
            .clickable { onClick(category) }
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val icon = when (category) {
            Category.Sleep -> Icons.Default.Nightlight
            Category.StressRelief -> Icons.Default.Healing
            Category.Focus -> Icons.Default.SelfImprovement
            Category.Energy -> Icons.Default.Bolt
            Category.QuickBreak -> Icons.Default.Book
        }

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = contentColor
        )
        Text(
            text = category.name,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium,
            color = contentColor
        )
    }
}

@Composable
fun MoodGoalSelectionRow(
    categories: List<Category>,
    selectedCategory: Category?,
    onCategoryClick: (Category) -> Unit
) {
    SectionTitle(title = "Breathe for Your Mood")

    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        categories.forEach { category ->
            val isSelected = category == selectedCategory
            GoalChip(
                category = category,
                isSelected = isSelected,
                onClick = onCategoryClick
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MinimalFloatingTopBar(title: String, onSettingsClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        )
    )
}

@Composable
fun FloatingGetStartedAction(primaryColor: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .clickable { onClick() }
            .graphicsLayer {
                shadowElevation = 8f
                shape = RoundedCornerShape(24.dp)
                clip = true
            },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            primaryColor.copy(alpha = 0.95f),
                            primaryColor.copy(alpha = 0.7f)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Breathing Tutorial & Guide",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Learn the basics and how to use the app.",
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun FlowExerciseCard(
    title: String,
    description: String,
    color: Color,
    onClick: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            color.copy(alpha = 1.0f),
            color.copy(alpha = 0.5f)
        )
    )

    Card(
        modifier = Modifier
            .width(200.dp)
            .height(280.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.BottomEnd)
                    .background(color.copy(alpha = 0.3f), CircleShape)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f),
                        maxLines = 4
                    )
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier.padding(horizontal = 24.dp, vertical = 8.dp)
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        viewModel = HomeViewModel(
            trainingsRepository = AppContainer.trainingsRepository,
            preferencesRepository = AppContainer.preferencesRepository
        ),
        onExerciseClick = {},
        onSettingsClick = {},
        onGetStartedClick = {},
        onCategoryClick = {}
    )
}
