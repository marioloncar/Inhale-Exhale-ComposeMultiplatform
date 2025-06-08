package com.autogenie.autogenic.feature.tutorial.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialScreen(
    onBackClick: () -> Unit
) {
    var isInhale by remember { mutableStateOf(true) }

    val pulseTransition = rememberInfiniteTransition(label = "breathing_pulse")
    val size by pulseTransition.animateFloat(
        initialValue = 120f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            isInhale = !isInhale
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("How to Breathe", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.SelfImprovement, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(size.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color(0xFF80D8FF), Color(0xFF039BE5)),
                            radius = size * 0.6f
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isInhale) "Inhale" else "Exhale",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Instructions
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    "Breathing Basics",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text("1. Sit or lie down comfortably in a quiet place.")
                Text("2. Close your eyes gently and relax your body.")
                Text("3. Inhale slowly through your nose for 4 seconds.")
                Text("4. Hold your breath for 4 seconds.")
                Text("5. Exhale slowly through your mouth for 6 seconds.")
                Text("6. Repeat for several minutes.")

                Text(
                    "Why It Helps",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text("Deep breathing activates the parasympathetic nervous system, which calms your body and reduces stress.")
            }

            Text(
                text = "You're doing great! ✨",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
