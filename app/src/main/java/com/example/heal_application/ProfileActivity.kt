package com.example.heal_application

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
class ProfileActivity : AppCompatActivity() {

    private lateinit var logoutButton : Button
    private lateinit var homeButton: Button
    private lateinit var profileName : TextView
    private lateinit var profileEmail : TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser : FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!

        logoutButton = findViewById(R.id.logoutButton)
        homeButton = findViewById(R.id.homeButton)
        profileName = findViewById(R.id.profileName)
        profileEmail = findViewById(R.id.profileEmail)

        //Display user's details
        profileName.text = currentUser.displayName?: "Unknown User"
        profileEmail.text = currentUser.email?: "No email found"

        homeButton.setOnClickListener {
            // Navigate back to the HOME screen
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish() //Close Profile Activity
        }

        logoutButton.setOnClickListener {
            // Sign out the user
            auth.signOut()

            // Navigate back to the HOME screen
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()  // Close Profile Activity
        }
    }
}