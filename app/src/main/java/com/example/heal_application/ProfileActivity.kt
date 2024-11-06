package com.example.heal_application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileActivity : BaseActivity() {

    private lateinit var logoutButton : Button
    private lateinit var homeButton: Button
    private lateinit var profileName : TextView
    private lateinit var profileEmail : TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser : FirebaseUser
    private lateinit var editProfileButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_profile, findViewById(R.id.content_frame))

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser!!

        logoutButton = findViewById(R.id.logoutButton)
        homeButton = findViewById(R.id.homeButton)
        profileName = findViewById(R.id.profileName)
        profileEmail = findViewById(R.id.profileEmail)
        editProfileButton = findViewById(R.id.editProfileButton)


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

        editProfileButton.setOnClickListener {
            val intent = Intent(this,EditProfile::class.java)
            startActivity(intent)
            finish() //Close Profile Activity
        }
    }
}
