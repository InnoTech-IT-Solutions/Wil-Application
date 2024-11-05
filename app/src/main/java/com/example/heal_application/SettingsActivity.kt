package com.example.heal_application

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial


class SettingsActivity : AppCompatActivity() {

    private lateinit var notificationsSwitch: SwitchMaterial
    private lateinit var darkModeSwitch: SwitchMaterial
    private lateinit var settingsRepository: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize views

        notificationsSwitch = findViewById(R.id.notificationsSwitch)
        darkModeSwitch = findViewById(R.id.darkModeSwitch)
        val saveSettingsButton = findViewById<MaterialButton>(R.id.saveSettingsButton)

        settingsRepository = SettingsRepository()

        // Save settings when the save button is clicked
        saveSettingsButton.setOnClickListener {
            saveUserSettings()
        }

        // Load user settings
        loadUserSettings()
    }

    private fun loadUserSettings() {
        settingsRepository.loadSettings(onSuccess = { settings ->
            settings?.let {

                notificationsSwitch.isChecked = it["notifications_enabled"] as? Boolean ?: false
                darkModeSwitch.isChecked = it["dark_mode"] as? Boolean ?: false
            }
        }, onFailure = {
            Toast.makeText(this, "Failed to load settings", Toast.LENGTH_SHORT).show()
        })
    }

    private fun saveUserSettings() {
        val settings = mapOf(

            "notifications_enabled" to notificationsSwitch.isChecked,
            "dark_mode" to darkModeSwitch.isChecked
        )

        settingsRepository.saveSettings(settings, onSuccess = {
            Toast.makeText(this, "Settings saved successfully", Toast.LENGTH_SHORT).show()
        }, onFailure = {
            Toast.makeText(this, "Failed to save settings", Toast.LENGTH_SHORT).show()
        })
    }
}
