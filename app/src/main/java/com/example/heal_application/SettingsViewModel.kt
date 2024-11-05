// SettingsViewModel.kt
package com.example.heal_application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val repository = SettingsRepository()

    private val _language = MutableStateFlow("")
    val language: StateFlow<String> = _language

    private val _notificationsEnabled = MutableStateFlow(false)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    private val _darkModeEnabled = MutableStateFlow(false)
    val darkModeEnabled: StateFlow<Boolean> = _darkModeEnabled

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            repository.loadSettings(
                onSuccess = { settings ->
                    _language.value = settings?.get("language") as? String ?: ""
                    _notificationsEnabled.value = settings?.get("notifications_enabled") as? Boolean ?: false
                    _darkModeEnabled.value = settings?.get("dark_mode") as? Boolean ?: false
                },
                onFailure = { /* Handle failure */ }
            )
        }
    }

    fun onLanguageChange(newLanguage: String) {
        _language.value = newLanguage
    }

    fun onNotificationsToggle(isEnabled: Boolean) {
        _notificationsEnabled.value = isEnabled
    }

    fun onDarkModeToggle(isEnabled: Boolean) {
        _darkModeEnabled.value = isEnabled
    }

    fun saveSettings() {
        viewModelScope.launch {
            val settings = mapOf(
                "language" to _language.value,
                "notifications_enabled" to _notificationsEnabled.value,
                "dark_mode" to _darkModeEnabled.value
            )
            repository.saveSettings(settings, onSuccess = {
                // Optionally show a success message
            }, onFailure = {
                // Optionally show an error message
            })
        }
    }
}
