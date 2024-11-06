package com.example.heal_application

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class PrivacyPolicyActivity : AppCompatActivity() {

    private lateinit var homeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        // Initialize the home button and set its click listener inside onCreate
        homeButton = findViewById(R.id.homeButton)

        homeButton.setOnClickListener {
            // Create an intent to navigate to HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()  // Close the current activity
        }
    }
}
