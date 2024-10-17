package com.example.heal_application

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPasswordButton: TextView
    private lateinit var signupButton: TextView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Firebase Auth
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton)
        signupButton = findViewById(R.id.signupButton)

        //Highlighting "Sign up!"
        val fulltext = "Don't have an account? Sign Up!"
        val spannableString = SpannableString(fulltext)

        //Setting color for "Sign Up!"
        val signupColorSpan = ForegroundColorSpan(Color.BLUE)
        val startIndex = fulltext.indexOf("Sign Up!")
        val endIndex = startIndex + "Sign Up!".length
        spannableString.setSpan(
            signupColorSpan,
            startIndex,
            endIndex,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Setting spannable to signupButton TV
        signupButton.text = spannableString

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }

        forgotPasswordButton.setOnClickListener {
            // Navigate to Forgot Password screen
        }

        signupButton.setOnClickListener {
            // Navigate to Signup screen
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Get the currently signed-in user
                    val user = auth.currentUser
                    if (user != null) {
                        // Check the user role
                        checkUserRole(user)
                    } else {
                        Toast.makeText(this, "User data not found. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // If sign-in fails, display a message to the user.
                    Toast.makeText(this, "Authentication failed. Please check your email and password.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkUserRole(user: FirebaseUser) {
        val emailKey = user.email?.replace(".", "_")?.replace(",", "_") ?: return

        // Debugging: log the formatted email key
        Log.d("LoginActivity", "Checking role for email key: $emailKey")

        database.child("users").child(emailKey).child("role")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val role = snapshot.getValue(String::class.java)
                    Log.d("LoginActivity", "Retrieved role: $role")

                    if (role == "admin") {
                        Toast.makeText(this@LoginActivity, "Welcome, Admin!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, AdminDashboard::class.java)
                        startActivity(intent)
                    } else if (role == null) {
                        // If role is not found
                        Log.e("LoginActivity", "Role not found for user: $emailKey")
                        Toast.makeText(this@LoginActivity, "Error: Role not found.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@LoginActivity, "Welcome, Member!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }

                    finish() // Close the LoginActivity
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("LoginActivity", "Error retrieving user role: ${error.message}")
                    Toast.makeText(this@LoginActivity, "Error retrieving role: ${error.message}", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
    }



}