package com.example.heal_application

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SettingsRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    // Save user settings
    fun saveSettings(settings: Map<String, Any>, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        database.child("users").child(userId).child("settings")
            .setValue(settings)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    // Load user settings
    fun loadSettings(onSuccess: (Map<String, Any>?) -> Unit, onFailure: (Exception) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        database.child("users").child(userId).child("settings")
            .get()
            .addOnSuccessListener { snapshot ->
                val settings = snapshot.value as? Map<String, Any>
                onSuccess(settings)
            }
            .addOnFailureListener { onFailure(it) }
    }
}