// SettingsActivity.kt
package com.example.heal_application

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText

class SettingsActivity : AppCompatActivity() {
    private lateinit var languageEditText: EditText
    private lateinit var notificationsSwitch: Switch
    private lateinit var darkModeSwitch: Switch
    private lateinit var settingsRepository: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize views
        languageEditText = findViewById(R.id.languageEditText)
        notificationsSwitch = findViewById(R.id.notificationsSwitch)
        darkModeSwitch = findViewById(R.id.darkModeSwitch)
        settingsRepository = SettingsRepository()
        val notificationsSwitch = findViewById<SwitchMaterial>(R.id.notificationsSwitch)
        val darkModeSwitch = findViewById<SwitchMaterial>(R.id.darkModeSwitch)
        val languageEditText = findViewById<TextInputEditText>(R.id.languageEditText)
        val saveSettingsButton = findViewById<MaterialButton>(R.id.saveSettingsButton)

        // Save settings when the save button is clicked
        findViewById<Button>(R.id.saveSettingsButton).setOnClickListener {
            saveUserSettings()
        }

        // Load user settings
        loadUserSettings()
    }

    private fun loadUserSettings() {
        settingsRepository.loadSettings(onSuccess = { settings ->
            // Populate the fields with existing settings
            settings?.let {
                languageEditText.setText(it["language"] as? String ?: "")
                notificationsSwitch.isChecked = it["notifications_enabled"] as? Boolean ?: false
                darkModeSwitch.isChecked = it["dark_mode"] as? Boolean ?: false
            }
        }, onFailure = {
            Toast.makeText(this, "Failed to load settings", Toast.LENGTH_SHORT).show()
        })
    }

    private fun saveUserSettings() {
        val settings = mapOf(
            "language" to languageEditText.text.toString(),
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
