package com.autogenie.autogenic.feature.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.autogenie.autogenic.feature.home.ui.toColor
import com.autogenie.autogenic.feature.settings.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text("Select a theme for your exercises:", fontSize = 18.sp)
            }

            uiState.availableThemes.forEach { (label, colors) ->
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.setTheme(label) }
                            .padding(8.dp)
                            .border(
                                width = if (uiState.selectedThemeId == label) 1.dp else 0.dp,
                                color = if (uiState.selectedThemeId == label) MaterialTheme.colorScheme.primary else Color.LightGray,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(8.dp)
                    ) {
                        Text(text = label, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            colors.forEach { color ->
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(color = color.toColor(), shape = CircleShape)
                                        .border(1.dp, Color.Gray, shape = CircleShape)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
