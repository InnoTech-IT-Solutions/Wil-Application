package com.example.heal_application

import android.content.Intent
import android.os.Bundle
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest


class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signupButton: Button
    private lateinit var loginTextButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance() //Initializing Firebase Auth
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        signupButton = findViewById(R.id.signupButton)
        loginTextButton = findViewById(R.id.loginTextButton)


        //Highlighting "Log In!"
        val fulltext = "Already have an account? Log In!"
        val spannableString = SpannableString(fulltext)

        //Setting color for "Log In!"
        val loginColorSpan = ForegroundColorSpan(Color.BLUE)
        val startIndex = fulltext.indexOf("Log In!")
        val endIndex = startIndex + "Log In!".length
        spannableString.setSpan(
            loginColorSpan,
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Setting spannable to loginTextButton TV
        loginTextButton.text = spannableString

        signupButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else if (!isPasswordValid(password)) {
                // Show an error message if the password does not meet the requirements
                Toast.makeText(this, "Password must be at least 8 characters long, include a mix of upper and lower case letters, numbers, and special characters.", Toast.LENGTH_LONG).show()
            } else {
                createUser(email,password,name)
            }
        }

        loginTextButton.setOnClickListener {
            // Navigate to Login screen
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun createUser(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Sign in successful...UI Updated
                    val user = auth.currentUser
                    updateUI(user, name)
                } else {
                    // If sign in fails, display message
                    Toast.makeText(
                        baseContext, "User Authentication Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null, name)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?, name: String) {
        if (user != null) {
            // Save the user's name to the Firebase user profile
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()

            // Update the user's profile with the new display name
            user.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Redirect to home or login screen
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        // Handle the failure case if needed
                        Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    // Password validation function
    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}$")
        return passwordPattern.matches(password)
    }

        }

