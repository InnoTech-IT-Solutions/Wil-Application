package com.innotech.healapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class PrivacyPolicyActivity : BaseActivity() {

    private lateinit var homeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_privacy_policy, findViewById(R.id.content_frame))

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
