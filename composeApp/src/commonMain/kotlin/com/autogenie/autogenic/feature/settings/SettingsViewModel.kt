package com.autogenie.autogenic.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autogenie.autogenic.data.preferences.domain.PreferencesRepository
import com.autogenie.autogenic.feature.settings.ui.model.SettingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                preferencesRepository.observeAvailableThemes(),
                preferencesRepository.observeSelectedTheme(),
                ::Pair
            )
                .collect {
                    val (availableThemes, selectedThemeId) = it
                    _uiState.emit(
                        SettingsUiState(
                            availableThemes = availableThemes,
                            selectedThemeId = selectedThemeId.first
                        )
                    )
                }
        }
    }

    fun setTheme(id: String) {
        viewModelScope.launch {
            preferencesRepository.setTheme(id)
        }
    }
}
