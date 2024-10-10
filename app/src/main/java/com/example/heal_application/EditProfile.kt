package com.example.heal_application

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class EditProfile : BaseActivity() {

    private lateinit var currentUser: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var profileName: EditText
    private lateinit var profileEmail: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_edit_profile, findViewById(R.id.content_frame))

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Get the current user
        currentUser = auth.currentUser ?: return

        // Initialize views
        profileName = findViewById(R.id.profileName)
        profileEmail = findViewById(R.id.profileEmail)
        saveButton = findViewById(R.id.saveProfileButton)
        cancelButton = findViewById(R.id.cancelButton)

        // Populate the EditTexts with current user details
        profileName.setText(currentUser.displayName ?: "Unknown User")
        profileEmail.setText(currentUser.email ?: "No email found")

        // Handle save button click
        saveButton.setOnClickListener {
            saveProfileChanges()
        }

        // Handle cancel button click
        cancelButton.setOnClickListener {
            finish() // Go back to the previous activity
        }
    }

    private fun saveProfileChanges() {
        // Implement the logic to save changes
        val newName = profileName.text.toString().trim()
        val newEmail = profileEmail.text.toString().trim()

        // Flag to track if the update process has started
        var updateSuccessful = true

        if (newName.isNotEmpty()) {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build()

            currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Name updated successfully!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "Failed to update name.", Toast.LENGTH_SHORT).show()
                        updateSuccessful = false
                    }
                }
        }

        if (newEmail.isNotEmpty() && newEmail != currentUser.email) {
            currentUser.updateEmail(newEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Email updated successfully!", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, "Failed to update email.", Toast.LENGTH_SHORT).show()
                        updateSuccessful = false
                    }
                }
        }
    }
}



