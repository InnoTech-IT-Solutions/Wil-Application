package com.example.heal_application

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HomeActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var profileTextView: TextView
    private lateinit var userImageView: ImageView
    private lateinit var eventsButton: Button
    private lateinit var settingsButton: Button
    private lateinit var calendarButton: Button
    private lateinit var aboutButton: Button
    private lateinit var leftButton: Button
    private lateinit var rightButton: Button
    private lateinit var centerImageView: ImageView
    private lateinit var auth: FirebaseAuth
    private lateinit var logoutButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        loginButton = findViewById(R.id.loginButton)
        profileTextView = findViewById(R.id.profileTextView)
        userImageView = findViewById(R.id.userImageView)
        eventsButton = findViewById(R.id.eventsButton)
        settingsButton = findViewById(R.id.settingsButton)
        calendarButton = findViewById(R.id.calendarButton)
        aboutButton = findViewById(R.id.aboutButton)
        leftButton = findViewById(R.id.leftButton)
        rightButton = findViewById(R.id.rightButton)
        centerImageView = findViewById(R.id.centerImageView)
        logoutButton = findViewById(R.id.logoutButton)

        //Checking if user is logged in
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            // User is logged in, hide the login button and show profile views and logout button
            Log.d("HomeActivity", "User is logged in: ${currentUser.email}")
            loginButton.visibility = View.GONE
            profileTextView.visibility = View.VISIBLE
            userImageView.setImageResource(R.drawable.placeholder)
            userImageView.visibility = View.VISIBLE
            logoutButton.visibility = View.GONE

            // You can optionally set the user's display name or image here
            profileTextView.text = "My Profile"
            // userImageView.setImageResource(R.drawable.user_icon) // You can use a custom icon
        } else {
            // User is not logged in, show the login button
            Log.d("HomeActivity", "No user is logged in")
            loginButton.visibility = View.VISIBLE
            profileTextView.visibility = View.GONE
            userImageView.visibility = View.GONE
            logoutButton.visibility = View.GONE

        }



        loginButton.setOnClickListener {
            // Navigate to Login screen
            startActivity(Intent(this, LoginActivity::class.java))
        }

        logoutButton.setOnClickListener {
            // Sign out the user
            auth.signOut()

            // Update UI to show the login button and hide profile/logout views
            loginButton.visibility = View.VISIBLE
            profileTextView.visibility = View.GONE
            userImageView.visibility = View.GONE
            logoutButton.visibility = View.GONE

            // Navigate back to the login screen
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()  // Close HomeActivity
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
        profileTextView.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        userImageView.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

    }
}
