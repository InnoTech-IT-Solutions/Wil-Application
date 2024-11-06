package com.example.heal_application

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class DonationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_donation, findViewById(R.id.content_frame))

        // Home button functionality
        val homeButton = findViewById<Button>(R.id.homeButton)
        homeButton?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}
