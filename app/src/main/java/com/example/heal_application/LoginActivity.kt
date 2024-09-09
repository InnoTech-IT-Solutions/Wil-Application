package com.example.heal_application

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPasswordButton: TextView
    private lateinit var signupButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Firebase Auth
        auth = FirebaseAuth.getInstance()

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
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT)
                    .show()
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
                    // Sign in success, navigate to home screen
                    Toast.makeText(
                        this,
                        "Login Successful!",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish() // Close the LoginActivity
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this,
                        "Authentication failed. Please check your email and password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}