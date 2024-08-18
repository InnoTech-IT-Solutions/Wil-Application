package com.example.heal_application

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView

class HomeActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var eventsButton: Button
    private lateinit var settingsButton: Button
    private lateinit var calendarButton: Button
    private lateinit var aboutButton: Button
    private lateinit var leftButton: Button
    private lateinit var rightButton: Button
    private lateinit var centerImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        loginButton = findViewById(R.id.loginButton)
        eventsButton = findViewById(R.id.eventsButton)
        settingsButton = findViewById(R.id.settingsButton)
        calendarButton = findViewById(R.id.calendarButton)
        aboutButton = findViewById(R.id.aboutButton)
        leftButton = findViewById(R.id.leftButton)
        rightButton = findViewById(R.id.rightButton)
        centerImageView = findViewById(R.id.centerImageView)

        loginButton.setOnClickListener {
            // Navigate to Login screen
            startActivity(Intent(this, LoginActivity::class.java))
        }

        eventsButton.setOnClickListener {
            // Handle events navigation
        }

        settingsButton.setOnClickListener {
            // Handle settings navigation
        }

        calendarButton.setOnClickListener {
            // Handle calendar navigation
        }

        aboutButton.setOnClickListener {
            // Handle about navigation
        }

        leftButton.setOnClickListener {
            // Handle left image navigation
        }

        rightButton.setOnClickListener {
            // Handle right image navigation
        }
    }
}
