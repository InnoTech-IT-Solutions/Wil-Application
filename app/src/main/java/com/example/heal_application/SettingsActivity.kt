package com.example.heal_application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : BaseActivity() {

    private lateinit var homeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_settings, findViewById(R.id.content_frame))

        // Load the SettingsFragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()

        homeButton = findViewById(R.id.homeButton)


        homeButton.setOnClickListener {
            // Navigate back to the HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Close the SettingsActivity
        }




    }
}