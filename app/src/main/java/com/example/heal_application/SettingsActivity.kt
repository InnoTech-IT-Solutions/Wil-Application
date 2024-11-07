package com.example.heal_application

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.google.firebase.auth.FirebaseUser

class SettingsActivity : BaseActivity() {

    private lateinit var notificationsSwitch: SwitchMaterial


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_settings, findViewById(R.id.content_frame))

        auth = FirebaseAuth.getInstance()
        // Initialize views
        notificationsSwitch = findViewById(R.id.notificationsSwitch)


        val saveSettingsButton = findViewById<MaterialButton>(R.id.saveSettingsButton)

        // Firebase authentication instance
        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            Toast.makeText(this, "You must be logged in to save settings", Toast.LENGTH_SHORT).show()
            return
        }
        val currentUser: FirebaseUser? = auth.currentUser

        // Save settings when the save button is clicked
        saveSettingsButton.setOnClickListener {
            saveUserSettings(user.email ?: "")  // Pass the user's email
        }

        // Load user settings if available
        loadUserSettings(user.email ?: "")

        // Monitor changes in the notification switch
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Enable notifications (subscribe to the topic)
                subscribeToNotifications()
                saveUserSettingsLocally(this, true)  // Save locally when subscribed
            } else {
                // Disable notifications (unsubscribe from the topic)
                unsubscribeFromNotifications()
                saveUserSettingsLocally(this, false)  // Save locally when unsubscribed
            }
        }
    }

    private fun loadUserSettings(email: String) {
        val ref = FirebaseDatabase.getInstance().getReference("users").child(email.replace('.', '_'))

        ref.get().addOnSuccessListener {
            val notificationsEnabled = it.child("notifications_enabled").getValue(Boolean::class.java) ?: false


            notificationsSwitch.isChecked = notificationsEnabled

        }.addOnFailureListener {
            Toast.makeText(this, "Failed to load settings", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUserSettings(email: String) {
        val settings = mapOf(
            "notifications_enabled" to notificationsSwitch.isChecked,

        )

        val ref = FirebaseDatabase.getInstance().getReference("users").child(email.replace('.', '_'))

        ref.updateChildren(settings).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Settings saved successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to save settings", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Save user settings locally
    private fun saveUserSettingsLocally(context: Context, isSubscribed: Boolean) {
        val sharedPreferences = context.getSharedPreferences("UserSettings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isSubscribed", isSubscribed)
        editor.apply()  // or commit() for synchronous saving
    }

    // Retrieve user settings locally
    private fun getUserSettingsLocally(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("UserSettings", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isSubscribed", false)  // Default value is false
    }

    private fun subscribeToNotifications() {
        // Subscribe to the notifications topic
        FirebaseMessaging.getInstance().subscribeToTopic("notifications")
            .addOnCompleteListener { task ->
                var msg = "Subscribed to notifications"
                if (!task.isSuccessful) {
                    msg = "Subscription failed"
                }
                Log.d(TAG, msg)
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
    }

    private fun unsubscribeFromNotifications() {
        // Unsubscribe from the notifications topic
        FirebaseMessaging.getInstance().unsubscribeFromTopic("notifications")
            .addOnCompleteListener { task ->
                var msg = "Unsubscribed from notifications"
                if (!task.isSuccessful) {
                    msg = "Unsubscribing failed"
                }
                Log.d(TAG, msg)
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        private const val TAG = "SettingsActivity"
    }
}
