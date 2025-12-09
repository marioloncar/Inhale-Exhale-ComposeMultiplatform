package com.autogenie.inhaleexhale.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autogenie.inhaleexhale.data.preferences.domain.PreferencesRepository
import com.autogenie.inhaleexhale.feature.settings.ui.model.SettingsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
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
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                preferencesRepository.observeAvailableThemes(),
                preferencesRepository.observeUserData(),
                ::Pair
            )
                .collect {
                    val (availableThemes, userData) = it
                    _uiState.emit(
                        SettingsUiState(
                            availableThemes = availableThemes,
                            selectedThemeId = userData.selectedTheme.first,
                            isInfiniteCycle = userData.useInfiniteCycles
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

    fun setInfiniteCycle(isInfinite: Boolean) {
        viewModelScope.launch {
            preferencesRepository.setInfiniteCycle(isInfinite)
        }
    }
}
