package com.example.heal_application

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*

class EventsActivity : AppCompatActivity() {

    private lateinit var homeButton: Button
    private lateinit var calendarButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var customAdapter: CustomAdapter
    private lateinit var database: DatabaseReference
    private val eventsList = ArrayList<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        // Initialize Firebase reference
        database = FirebaseDatabase.getInstance().reference

        // Initialize buttons and set click listeners
        homeButton = findViewById(R.id.homeButton)
        calendarButton = findViewById(R.id.calendarButton)

        homeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        calendarButton.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Set up RecyclerView with CustomAdapter
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        customAdapter = CustomAdapter(eventsList)
        recyclerView.adapter = customAdapter

        // Fetch events from Firebase
        loadEventsFromFirebase()
    }

    private fun loadEventsFromFirebase() {
        database.child("events").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dateSnapshot in snapshot.children) {
                        val date = dateSnapshot.key ?: continue
                        for (eventSnapshot in dateSnapshot.children) {
                            val title = eventSnapshot.child("title").getValue(String::class.java) ?: "Untitled Event"
                            val description = eventSnapshot.child("description").getValue(String::class.java) ?: "No description"
                            val event = Event(date, title, description)
                            eventsList.add(event)
                        }
                    }
                    customAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@EventsActivity, "No events found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("EventsActivity", "Error loading events: ${error.message}")
                Toast.makeText(this@EventsActivity, "Error loading events", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
