package com.autogenie.inhaleexhale.feature.home.ui.model

import com.autogenie.inhaleexhale.data.trainings.domain.model.Category
import com.autogenie.inhaleexhale.feature.home.TrainingWithColor

data class HomeUiState(
    val moodCategories: List<Category> = emptyList(),
    val trainings: List<TrainingWithColor> = emptyList(),
    val selectedCategory: Category? = null
)
