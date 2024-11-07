package com.example.heal_application

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button

class EventsActivity : AppCompatActivity() {

    private lateinit var homeButton: Button
    private lateinit var calendarButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        homeButton = findViewById(R.id.homeButton)
        calendarButton = findViewById(R.id.calendarButton)

        homeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()  // Close EventsActivity
        }

        calendarButton.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
            finish()  // Close EventsActivity
        }

        // Initialize the RecyclerView with the CustomAdapter
        val dataset = arrayOf("Event 1", "Event 2", "Event 3") // Sample dataset for events
        val customAdapter = CustomAdapter(dataset)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customAdapter
        recyclerView.setItemViewCacheSize(20)
        recyclerView.setHasFixedSize(true)
    }
}
