package com.example.heal_application

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class AddEventActivity : BaseActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var eventDateEditText: EditText
    private lateinit var eventTitleEditText: EditText
    private lateinit var eventDescriptionEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_add_event, findViewById(R.id.content_frame))

        database = FirebaseDatabase.getInstance().reference.child("events")
        eventDateEditText = findViewById(R.id.eventDateEditText)
        eventTitleEditText = findViewById(R.id.eventTitleEditText) // New field for title
        eventDescriptionEditText = findViewById(R.id.eventDescriptionEditText)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            saveEvent()
        }
    }

    private fun saveEvent() {
        val date = eventDateEditText.text.toString().trim()
        val title = eventTitleEditText.text.toString().trim()
        val description = eventDescriptionEditText.text.toString().trim()

        if (date.isEmpty() || title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            return
        }

        // Save to Firebase with structured data
        val eventData = mapOf(
            "title" to title,
            "description" to description
        )

        database.child(date).push().setValue(eventData)
            .addOnSuccessListener {
                Toast.makeText(this, "Event added", Toast.LENGTH_SHORT).show()
                finish() // Close the activity after saving
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add event", Toast.LENGTH_SHORT).show()
            }
    }
}
