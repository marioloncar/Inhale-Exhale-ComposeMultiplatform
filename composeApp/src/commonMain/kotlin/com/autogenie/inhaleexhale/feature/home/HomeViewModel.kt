package com.autogenie.inhaleexhale.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autogenie.inhaleexhale.data.preferences.domain.PreferencesRepository
import com.autogenie.inhaleexhale.data.trainings.domain.TrainingsRepository
import com.autogenie.inhaleexhale.data.trainings.domain.model.Category
import com.autogenie.inhaleexhale.data.trainings.domain.model.Training
import com.autogenie.inhaleexhale.feature.home.ui.model.HomeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val trainingsRepository: TrainingsRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                trainingsRepository.trainings(),
                preferencesRepository.observeUserData().map { it.selectedTheme.second },
                _uiState.map { it.selectedCategory }.distinctUntilChanged(),
            ) { trainings, colors, selectedCategory ->
                val filteredTrainings = if (selectedCategory == null) {
                    trainings
                } else {
                    trainings.filter { training ->
                        training.category == selectedCategory
                    }
                }

                filteredTrainings.mapIndexed { index, training ->
                    TrainingWithColor(
                        training = training,
                        color = colors[index % colors.size]
                    )
                }
            }
                .collect { trainingsWithColor ->
                    _uiState.update { it.copy(trainings = trainingsWithColor) }
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            trainingsRepository.categories()
                .collect { categories ->
                    _uiState.update { it.copy(moodCategories = categories) }
                }
        }
    }

    fun onCategoryClicked(category: Category) {
        val newCategory = if (_uiState.value.selectedCategory == category) null else category
        _uiState.update { it.copy(selectedCategory = newCategory) }
    }
}

data class TrainingWithColor(
    val training: Training,
    val color: String
)